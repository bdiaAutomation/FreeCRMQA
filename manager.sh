#!/opt/homebrew/bin/bash
clear

# Function to simulate typing effect
type_text() {
  local text="$1"
  for ((i=0; i<${#text}; i++)); do
    echo -n "${text:$i:1}"
    sleep 0.05  # Adjust speed here
  done
  echo
}

# Colors
BLUE="\033[1;34m"
GREEN="\033[1;32m"
RESET="\033[0m"

# Animated Title
echo -e "${BLUE}=======================================${RESET}"
echo -ne "${GREEN}"
type_text "     Mobile Farm Node Manager"
echo -e "${BLUE}=======================================${RESET}"

#declare -A tells Bash you're creating an associative array (key-value map).
declare -A models=(
    ["IXS"]="/Users/bamba/Desktop/Grid_conf/iOS/iPhone_xs"
    ["I14"]="/Users/bamba/Desktop/Grid_conf/iOS/iPhone_14"
    ["I11"]="/Users/bamba/Desktop/Grid_conf/iOS/iPhone_11"
    ["I12"]="/Users/bamba/Desktop/Grid_conf/iOS/iPhone_12"
    ["S22"]="/Users/bamba/Desktop/Grid_conf/Android/galaxy_s22"
    ["A53"]="/Users/bamba/Desktop/Grid_conf/Android/galaxy_a53"
    ["NOV"]="/Users/bamba/Desktop/Grid_conf/Android/huawei_nov"
    ["S21"]="/Users/bamba/Desktop/Grid_conf/Android/galaxy_s21"
)

# Function: basename strips the path and returns only the final segment
 # basename "/Users/bamba/Desktop/Grid_conf/Android/galaxy_s22"  ➜ galaxy_s22
get_base_name() {
    local path="${models[$1]}"
    basename "$path"
}

model_names=( "IXS" "I14" "I11" "I12" "S22" "A53" "NOV" "S21" )

# Function : launch a model 
launch() {
    local input="$1"
    local model="${input^^}"  # Normalize to uppercase

    # Validate model
    if [[ -z "${models[$model]}" ]]; then
        echo "❌ Unknown model: $input"
        return 1
    fi

    local model_path="${models[$model]}"
    local config_base
    config_base=$(basename "$model_path")

    # Change to the model's directory
    cd "$model_path" || {
        echo "❌ Failed to change directory to $model_path"
        return 1
    }

    # Construct config file names
    local appium_config="appium_${config_base}.yml"
    local selenium_config="${config_base}.toml"

    # Display what will be launched
    echo "📁 Launching for model: $model"
    echo "🛠️  Appium config: $appium_config"
    echo "🧩 Selenium config: $selenium_config"

    # Launch Appium
    nohup appium --config "$appium_config" </dev/null >/dev/null 2>&1 &

    # Launch Selenium Grid node
    nohup java -jar ../../selenium-server-4.15.0.jar node \
        --config "$selenium_config" \
        --publish-events tcp://mobile-farm-pub-aks.ari.internal.auchan.com:4442 \
        --subscribe-events tcp://mobile-farm-pub-aks.ari.internal.auchan.com:4443 \
        --grid-url https://mobile-farm-aks.ari.internal.auchan.com \
        </dev/null >/dev/null 2>&1 &

    echo -e "\033[1;32m✅ $model successfully launched\033[0m"
}


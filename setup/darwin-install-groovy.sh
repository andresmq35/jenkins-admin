#!/usr/bin/env bash
brew install groovy
which groovy
BASHRC=$HOME/.bashrc
OPENRC=$HOME/.openrc

show_brew_info_as_json() {
  brew info --json=v1 groovy
}

java_latest_install() {
  brew info --json=v1 openjdk
  brew install openjdk
}

add_openrc_to_bashrc () {
  echo "source $OPENRC" >> $BASHRC
}

# Check if we have already added groovy home
check_openrc_has_groovy() {
  if grep -Fxq "GROOVY_HOME" $OPENRC; then
      echo 'GROOVY_HOME already set'
  else
      echo 'Adding openrc file source to bashrc'
      add_openrc_to_bashrc
  fi
}

add_groovy_home() {
  echo "export GROOVY_HOME=/usr/local/opt/groovy/libexec" >> $OPENRC
}

add_java_home() {
  echo 'export PATH="/usr/local/opt/openjdk/bin:$PATH"' >> $OPENRC
}

main() {
  if [ -f "$BASHRC" ]; then
    check_openrc_has_groovy
  else
    touch $BASHRC
  fi
  add_groovy_home
}

main

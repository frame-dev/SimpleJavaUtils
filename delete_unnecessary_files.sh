#!/usr/bin/env bash
set -euo pipefail

# Usage:
#   ./delete_unnecessary_files.sh [message] [--confirm|-c]

show_message=false
use_confirm=false

for arg in "$@"; do
  case "$arg" in
    message) show_message=true ;;
    --confirm|-c) use_confirm=true ;;
    *) ;;
  esac
done

# Collect matching files into an array (portable: works on macOS bash)
files=()
exec 3< <(find . -type f -name '._*' -print0)
while IFS= read -r -d '' f <&3; do
  files+=("$f")
done
exec 3<&-

if [[ ${#files[@]} -eq 0 ]]; then
  if $show_message; then
    echo "No \._* files found in $(pwd)."
  fi
  exit 0
fi

if $show_message; then
  echo "The following files will be deleted:"
  for f in "${files[@]}"; do
    echo "$f"
  done
  echo
fi

if ! $use_confirm; then
  for f in "${files[@]}"; do
    rm -f -- "$f"
  done
  if $show_message; then
    echo "Files deleted (no confirmation used)."
  fi
  exit 0
fi

read -r -p "Are you sure you want to delete these files? (y/n) " confirm
if [[ "$confirm" =~ ^[Yy]$ ]]; then
  for f in "${files[@]}"; do
    rm -f -- "$f"
  done
  echo "Files deleted."
else
  echo "Aborted."
fi

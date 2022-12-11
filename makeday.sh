day=$1
cp "src/template.kt" "src/Day$day.kt"
touch "src/Day${day}_test.txt"
touch "src/Day$day.txt"
git add "src/Day$day.kt"

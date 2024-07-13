g++ 1905095.cpp -o 1905095.out
for i in {1..54}
do
    ./1905095.out<"set1/g$i.rud">>"1905095_output.csv"
done
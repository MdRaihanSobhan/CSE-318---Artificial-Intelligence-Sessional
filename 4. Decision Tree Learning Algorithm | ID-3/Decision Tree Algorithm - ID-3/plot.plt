set terminal pdf
set output "graph.pdf"
set datafile separator ","

set title "Graph (Training Data Percentage vs Mean Accuracy"
set xlabel "Training Data Percentage"
set ylabel "Mean Accuracy"
plot "accuracy_metrics.csv" using 1:2 with lines title "Mean Accuracy"

set title "Graph (Training Data Percentage vs Standard Deviation of Mean Accuracy"
set xlabel "Training Data Percentage"
set ylabel "Mean Accuracy"
plot "accuracy_metrics.csv" using 1:3 with lines title "Stanard Deviation of Mean Accuracy"
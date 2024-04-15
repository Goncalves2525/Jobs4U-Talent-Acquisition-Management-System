main: main.o functions.o
	gcc main.o functions.o -z noexecstack -o main

main.o: main.c
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

functions.o: functions.c
	gcc -g -Wall -Wextra -fanalyzer -c functions.c -o functions.o

clean:
	rm -f *.o main

run: main
	./main

runargs: main
	./main input output report 5 60

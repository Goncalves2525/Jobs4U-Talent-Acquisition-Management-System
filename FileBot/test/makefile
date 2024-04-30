main: unity.o functions.o main.o
	gcc unity.o functions.o main.o -z noexecstack -o main
unity.o: unity.h unity.c
	gcc -g -Wall -Wextra -fanalyzer -c unity.c -o unity.o
functions.o: ../functions.h ../functions.c
	gcc -g -Wall -Wextra -fanalyzer -c ../functions.c -o functions.o
main.o: main.c
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o
clean:
	rm -f *.o main
run: main
	./main

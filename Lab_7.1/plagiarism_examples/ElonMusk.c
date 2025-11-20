#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void addItem(char *str, char **list, int size);
void removeItem(char *str, char **list, int size);
void printList(char **list, int size);
void bubbleSort(char **list, int size);


int main() {
    char **ptr = NULL;
    int elNr = 0;
    ptr = (char **)malloc(elNr * sizeof(char *));

    //read file
    FILE *file1 = fopen("test_strings.txt", "r");
    char maxsize[1024];
    while (fgets(maxsize, sizeof(maxsize), file1)) {
        addItem(maxsize, ptr, elNr);
    }
    fclose(file1);

    
    bubbleSort(ptr, elNr);

    //write to file
    FILE *file2 = fopen("sorted_strings.txt", "w");
    

    
    for (int i = 0; i < elNr; i++) {
        free(ptr[i]);
    }
    free(ptr);

    return 0;
}

void addItem(char *str, char **list, int size) {
    size++;
    list = (char **)realloc(list, size * sizeof(char *));
    int lenght = strlen(str);
    list[size - 1] = (char *)malloc(lenght + 1);
    strcpy(list[size - 1], str);
}

void removeItem(char *str, char **list, int size) {
    for (int i = 0; i < size; i++) {
        if (strcmp(list[i], str) == 0) {
            break;
        }
        free(list[i]);
    }

    for (int i = 0; i < size - 1; i++) {
        list[i] = list[i + 1];
    }
    size--;
    list = (char **)realloc(list, size * sizeof(char *));
}

void printList(char **list, int size) {
    for (int i = 0; i < size; i++) {
        printf("%s\n", list[i]);
    }
}

void bubbleSort(char **list, int size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            if (strcmp(list[j], list[j + 1]) > 0) {
                char *temp = list[j];
                list[j] = list[j + 1];
                list[j + 1] = temp;
            }
        }
    }
}





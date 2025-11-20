#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define SIZE 100

void Print_list();
void Add_item(char *);
void Remove_item(char *);
void Add_itemFromFile(char *);
void BubbleSort();
void ToFile(char *);

int elNR = 0;
char **list = NULL;

int main(){
    char **var1 = NULL;

    Add_item("SMTH1");
    Add_item("SMTH2");

    Print_list();
    printf("\n");

    Remove_item("SMTH2");
    Print_list();
    printf("\n");

    Add_item("SMTH123");
    Add_item("SMTH244");
    Print_list();
    printf("\n");

    Remove_item("SMTH");
    Print_list();
    printf("\n");

    Add_itemFromFile("text.txt");
    Print_list();

    printf("\nSorted:\n");
    BubbleSort();
    Print_list();
    printf("\n");

    ToFile("text2.txt");

    // for(int i = 0; i < elNR; i++)
    //     free(list[i]);
    free(list);
    return 0;
}

void Print_list(){
    for(int i = 0; i < elNR; i++){
        printf("%s\n", list[i]);
    }
}

void Add_item(char *word){

    list = realloc(list, ((elNR + 1) * sizeof(char *)));
    list[elNR] = malloc(sizeof(*word));
    strcpy(list[elNR], word);
    elNR++;
}

void Remove_item(char *word){
    int index = -1; //just for after to check

    for(int i = 0; i < elNR; i++){
        if(strcmp(list[i], word) == 0){
            index = i;
            break;
        }
    }
    if(index != -1){
        free(list[index]);
        for(int i = index; i < (elNR - 1); i++){
            list[i] = list[i + 1];
        }
        elNR--;
        list[elNR] = NULL;
        list = realloc(list, elNR * sizeof(char *));
    }
}

void Add_itemFromFile(char *filename){
    FILE *file = fopen(filename, "r");
    if(file == NULL){
        printf("Can't open file");
    }
    char line[SIZE];
    while(fgets(line, SIZE, file)){
        Add_item(line);
    }
    fclose(file);
}

void BubbleSort(){
    int size = elNR - 1;
    for (int i = 0; i < elNR - 2; i++){
        for (int j = 0; j < size; j++){
            if (strcmp(list[j], list[j+1]) > 0){
                char *temp = list[j + 1];
                list[j + 1] = list[j];
                list[j] = temp;
            }
        }
        size = size - 1;
    }
}

void ToFile(char *filename){
    FILE *file = fopen(filename, "w");

    for(int i = 0; i < elNR; i++){
        fprintf(file, list[i]);
    }
    fclose(file);
}
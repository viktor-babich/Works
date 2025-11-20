#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXLET 50
#define STRINGS 10
char **car = NULL;
int elNR = 0;

void print_list(char **ptr, int length) {
    
    for (int i = 0; i < length; i++)
    {
        printf("%d:%s\n", i+1, ptr[i]);   
    }    
}

char add_item(char *item){
    elNR++;
    car = realloc(car, elNR * sizeof(char*));
    int size = strlen(item);
    car[elNR-1] = malloc(sizeof(char)*(size + 1));
    strcpy(car[elNR-1], item);
}

void main() {
    
    car = malloc(1*sizeof(char*));

    for(int i = 0; i < elNR; i++){

        char *str = "String";
        car[i] = malloc((strlen(str) + 1)*sizeof(char));
        strcpy(car[i], str);

    }

    FILE *fileptr = fopen("data.txt", "r");
    char ch[STRINGS][MAXLET];

    if( fileptr){
        printf("file opened\n");
    } else {
        printf("file error");
        return;
    }

    for (int i = 0; i < STRINGS; i++){
        fgets(ch[i], MAXLET, fileptr);
    }

    for (int i = 0; i < STRINGS; i++){
        add_item(ch[i]);
    }
    

    print_list(car, elNR);
    printf("\n");

    
    for (int i = 0; i < STRINGS - 1; i++){
        for (int j = 0; j < STRINGS - i - 1; j++){
            if(strcmp(car[j], car[j+1]) > 0){
                char t[MAXLET];
                strcpy(t, car[j]);
                strcpy(car[j], car[j+1]);
                strcpy(car[j+1], t);
            }
        }
    }

    print_list(car, elNR);
    FILE *fils = fopen("textnext.txt", "w+");

    for (int i = 0; i < STRINGS; i++){
        fputs(car[i], fils);
    }

    fclose(fileptr);
    free(car);
}
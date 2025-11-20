#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define MAXLET 50
#define STRINGS 10

char **c = NULL;
int elNR = 0;

void print_list(char **list, int size){

    for(int i = 0; i < size; i++){
        
        printf("%d - %s\n", i + 1, list[i]);

    }
}

char add_item(char *item){
    elNR++;
    c = realloc(c, elNR * sizeof(char*));
    int size = strlen(item);
    c[elNR-1] = malloc(sizeof(char)*(size + 1));
    strcpy(c[elNR-1], item);
}

void remove_item(char *str){
    
int index = -1;
for(int i = 0; i < elNR; i++){
    if(strcmp(c[i], str) == 0){
    index = i;
    break;
    }
}

if(index == -1){
    printf("there is no such element");
}

free(c[index]);
for(int i = index; i < elNR - 1; i++){
    c[i] = c[i+1];
}

c[elNR - 1] = NULL;
elNR--;
c = realloc(c, elNR * sizeof(char*));

}

int main(){
    
    c = malloc(1*sizeof(char*));

    for(int i = 0; i < elNR; i++){

        char *str = "String";
        c[i] = malloc((strlen(str) + 1)*sizeof(char));
        strcpy(c[i], str);

    }

    FILE *fil;
    char ch[STRINGS][MAXLET];
    fil = fopen("text.txt", "r");

    if (fil == NULL){
        printf("File cannot be opened");
    }

    for (int i = 0; i < STRINGS; i++){
        fgets(ch[i], MAXLET, fil);
    }

    for (int i = 0; i < STRINGS; i++){
        add_item(ch[i]);
    }

    fclose(fil);

    print_list(c, elNR);

    for (int i = 0; i < STRINGS - 1; i++){
        for (int j = 0; j < STRINGS - i - 1; j++){
            if(strcmp(c[j], c[j+1]) > 0){
                char t[MAXLET];
                strcpy(t, c[j]);
                strcpy(c[j], c[j+1]);
                strcpy(c[j+1], t);
            }
        }
    }

    print_list(c, elNR);
    FILE *fils = fopen("textnext.txt", "w+");

    for (int i = 0; i < STRINGS; i++){
        fputs(c[i], fils);
    }
    fclose(fils);
    free(c);
}
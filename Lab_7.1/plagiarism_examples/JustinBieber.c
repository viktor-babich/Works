#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER_SIZE 128

void add_item(char***, int*, char*);
void remove_item(char***, int*, char*);
void print_list(char**, int);

void add_item(char*** list, int* size, char new[])
{
    *size += 1;
    *list = (char **)realloc(*list , sizeof(char*) * (*size));
    (*list)[(*size)-1] = malloc(sizeof(char) * (strlen(new) + 1));
    strcpy((*list)[(*size)-1], new);
}

void remove_item(char*** list, int* size, char* del)
{
    int index = -1;
    for(int i = 0; i < *size; i++)
    {
        if(!strcmp((*list)[i], del))
        {
            index = i;
            break;
        }
    }
    if(index == -1) return;
    free((*list)[index]);
    for( int i = index; i < *size - 1; i++)
    {
        (*list)[i] = (*list)[i + 1];
    }
    (*list)[*size - 1] = NULL;
    *size -= 1;
    (*list) = (char**)realloc(*list, sizeof(char*) * (*size));
}

void print_list(char** list, int elements)
{
    for(int i = 0; i < elements; i++)
        puts(list[i]);
}

int main()
{
    char** list;
    int elNR = 0;
    list = (char**)malloc(sizeof(char*));

    FILE *workfile;
    workfile = fopen("input.txt", "r");
    if(!workfile)
    {
        puts("Failed opening input file");
        exit(1);
    }

    char buffer[BUFFER_SIZE];
    while(fgets(buffer, BUFFER_SIZE, workfile))
        add_item(&list, &elNR, buffer); //important to note that lines have \n at the end
    
    //print_list(list, elNR);

    fclose(workfile);

    for(int i = 0; i < elNR - 1; i++)
    {
        for(int j = 0; j < elNR - i - 1; j++)
        {
            if(strcmp(list[j], list[j + 1]) > 0)
            {
                char* t = list[j];
                list[j] = list[j + 1];
                list[j + 1] = t;
            }
        }
    }
    //puts("SORTED:\n");
    //print_list(list, elNR);

    workfile = fopen("output.txt", "w");
    if(!workfile)
    {
        puts("Failed opening output file");
        exit(1);
    }

    for(int i = 0; i < elNR; i++)
    {
        fprintf(workfile, "%s", list[i]);
    }

    fclose(workfile);

    for(int i = 0; i <elNR; i++)
    {
        free(list[i]);
    }
    free(list);

    return 0;

}
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define SIZE 1024

void print_list(char **strL, int elNr);
void add_item(char ***strL, int *elNr, char *item);
void remove_item(char ***strL, int *elNr, char *item);
void bubble_sort(char **strL, int elNr);

int main()
{
    int elNr = 0;
    char **strL;
    strL = (char**)malloc(sizeof(char*));
    FILE *fpr = fopen("Textfile.txt", "r");
    FILE *fpw = fopen("sortedlist.txt", "w");
    if (!fpr || !fpw)
    {
    printf("Cannot open the file\n");
    exit(EXIT_FAILURE);
    }
    char input[4096];
    while (fgets(input,sizeof(input),fpr))
    {
        input[strcspn(input,"\n")] = 0;
        add_item(&strL, &elNr, input);
    }
    print_list(strL, elNr);
    remove_item(&strL, &elNr, "Donec nec urna molestie, aliquet tellus nec, maximus ante.");
    printf("List now:\n");
    print_list(strL, elNr);
    printf("List sorted:\n");
    bubble_sort(strL, elNr);
    print_list(strL, elNr);
    for (int i = 0; i < elNr; i++)
        fprintf(fpw, "%s\n", strL[i]);
    for (int i=0; i<elNr; i++)
        free(strL[i]);
    free(strL);
    fclose(fpr);
    fclose(fpw);
    return 0;
}

void print_list(char **strL, int elNr)
{
    for (int i=0; i<elNr; i++)
    {
        printf("%s\n",strL[i]);
    }
}

void add_item(char ***strL, int *elNr, char *item)
{
    (*elNr)++;
    *strL = (char**)realloc(*strL, (*elNr)*sizeof(char*));
    (*strL)[(*elNr)-1] = (char*)malloc((strlen(item)+1)*sizeof(char));
    strcpy((*strL)[(*elNr)-1],item);
}

void remove_item(char ***strL, int *elNr, char *item)
{
    int itemId = -1;
    for (int i=0; i<*elNr; i++)
    {
        if (strcmp((*strL)[i], item) == 0)
        {
            itemId = i;
            break;
        }
    }
    if (itemId != -1)
    {
        free((*strL)[itemId]);
        for (int i=itemId; i<(*elNr)-1; i++)
            (*strL)[i] = (*strL)[i+1];
        (*strL)[*elNr-1] = NULL;
        (*elNr)--;
        (*strL) = (char**)realloc(*strL, (*elNr)*sizeof(char*));
        printf("Item: '%s' was removed.\n",item);
    }
    else
        printf("Item: '%s' wasn't found.\n",item);
}

void bubble_sort(char **strL, int elNr)
{
    char *temp;
    for (int i = 0; i<elNr-1; i++)
    {
        for (int j = 0; j<elNr-i-1; j++)
        {
            if (strcmp(strL[j],strL[j+1]) > 0)
            {
                temp = strL[j];
                strL[j] = strL[j+1];
                strL[j+1] = temp;
            }
        }
    }
}




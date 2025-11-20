#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char **list = NULL;
int elNr = 0;

void printList()
{
    int i;
    for (i = 0; i < elNr; i++)
    {
        printf("%s\n", list[i]);
    }
}

void addItem(char *item)
{
    elNr++;
    list = realloc(list, sizeof(char*) * elNr);
    int itemLength = strlen(item);
    list[elNr - 1] = malloc(sizeof(char) * (itemLength + 1));
    strcpy(list[elNr - 1], item);
}

void removeItem(char *item)
{
    int i, itemIndex = -1;
    for (i = 0; i < elNr; i++)
    {
        if (strcmp(list[i], item) == 0)
        {
            itemIndex = i;
            break;
        }
    }
    if (itemIndex == -1)
    {
        printf("Item not found in the list.\n");
        return;
    }
    free(list[itemIndex]);
    for (i = itemIndex; i < elNr - 1; i++)
    {
        list[i] = list[i + 1];
    }
    list[elNr - 1] = NULL;
    elNr--;
    list = realloc(list, sizeof(char*) * elNr);
}

void bubbleSort() {
    int i, j;
    for (i = 0; i < elNr-1; i++) {
        for (j = 0; j < elNr-i-1; j++) {
            if (strcmp(list[j], list[j+1]) > 0) {
                char* temp = list[j];
                list[j] = list[j+1];
                list[j+1] = temp;
            }
        }
    }
}

int main()
{
    FILE *fptr;
    fptr = fopen("input.txt", "r");
    if (fptr == NULL) {
        printf("Error opening file.");
        return 0;
    }

    char str[100];
    while (fgets(str, 100, fptr) != NULL)
    {
        strtok(str, "\n");
        addItem(str);
    }
    fclose(fptr);

    printf("List before sorting:\n");
    printList();

    bubbleSort();

    printf("\nList after sorting:\n");
    printList();

    fptr = fopen("output.txt", "w");
    if (fptr == NULL) {
        printf("Error opening file.");
        return 0;
    }
    for (int i = 0; i < elNr; i++)
    {
        fputs(list[i], fptr);
        fputs("\n", fptr);
    }
}

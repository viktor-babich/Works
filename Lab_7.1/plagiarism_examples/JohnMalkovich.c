#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int elNr = 0;
char** list = NULL;

void print_list()
{
    printf("\n");
    for (int i = 0; i < elNr; i++)
        printf("[%d] %s\n", i, list[i]);
}

void add_item(char* str)
{
    elNr++;
    list = realloc(list, elNr * sizeof(char*));
    list[elNr - 1] = malloc(strlen(str) + 1);
    strcpy(list[elNr - 1], str);
}

void remove_item(char* item)
{
    int index = -1;
    for (int i = 0; i < elNr && index == -1; i++)
        if (strcmp(list[i], item) == 0)
            index = i;

    if (index != -1)
    {
        free(list[index]);
        for (int i = index; i < elNr - 1; i++)
            list[i] = list[i + 1];

        elNr--;
        list[elNr] = NULL;
        list = realloc(list, elNr * sizeof(char*));
    }
}

void add_file(char* file)
{
    FILE* fp = fopen(file, "r");
    
    char currentLine[100];

    while (fgets(currentLine, 100, fp))
        add_item(currentLine);

    fclose(fp);
}

void bubble_sort()
{
    int end = elNr - 1;
    for (int i = 0; i < elNr - 2; i++)
    {
        for (int j = 0; j < end; j++)
        {
            if (strcmp(list[j], list[j+1]) > 0)
            {
                char* temp = list[j + 1];
                list[j + 1] = list[j];
                list[j] = temp;
            }
        }
        end--;
    }
}

void save_to_file(char* file)
{
    FILE* fp = fopen(file, "w");

    for (int i = 0; i < elNr; i++)
        fprintf(fp, list[i]);

    fclose(fp);
}

int main(void)
{
    add_file("file.txt");
    print_list();

    bubble_sort();
    print_list();

    save_to_file("output.txt");

    free(list);

    return 0;
}
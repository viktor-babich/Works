#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_LENGTH 256

void read_from_file(char ** strings, const char * filename)
{
    FILE * fp;
    fp = fopen(filename, "r");
    char * line = (char *)malloc(MAX_LENGTH * sizeof(char));
    size_t len;
    int i = 0;
    while (getline(&line, &len, fp) != -1) {
        strings[i] = (char *)malloc(len * sizeof(char));
        strcpy(strings[i], line);
        i++;
    }
    free(line);
    fclose(fp);
}

void sort_strings(char ** strings, int n) 
{
    int i, j;
    for (i = 0; i < n-1; i++) {
        for (j = 0; j < n-i-1; j++) {
            if (strcmp(strings[j], strings[j+1]) > 0) 
            {
                char * temp = strings[j];
                strings[j] = strings[j+1];
                strings[j+1] = temp;
            }
        }
    }
}

void dump_to_file(char ** strings, const char * filename, int n) 
{
    FILE * fp;
    fp = fopen(filename, "w");
    int i;
    for (i = 0; i < n; i++) {
        fprintf(fp, "%s", strings[i]);
    }
    fclose(fp);
}

int main() {
    const char * filename = "strings.txt";
    char ** strings;
    int n = 10;
    strings = (char **)malloc(n * sizeof(char *));
    read_from_file(strings, filename);
}
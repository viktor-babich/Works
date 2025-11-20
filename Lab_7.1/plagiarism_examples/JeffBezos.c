// program accomplishes tasks 1 and 2, and returns segmentation fault.
// changing statement else{...} to:
// else{ fwrite(ptr[0], sizeof(char), sizeof(ptr1[0]), y);) }
// results in writing to a file some unformated text data.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void print_list(char** k, int l);
void add_item(char** k, char str[], int* l, char*** m);
int scmpr(const void* a, const void* b);
void bubble_sort(char*** m, int l);

int main(){
   
    char** p1 = NULL;
    char*** ptr1 = &p1;
    int elNr = 0; // number of elements in the list
    int* nr = &elNr; 
    
    //Memory allocation
    p1 = (char**)malloc(1 * sizeof(char));
    if(p1 == NULL){
    	printf("Memmory allocation fail\n");
    	exit(EXIT_FAILURE);
    }
    
    FILE* x;
    
    //Reading from file
    x = fopen("filename.txt", "r");
    
    if (!x) {
        printf("cannot open the file\n");
        free(p1);
        exit(EXIT_FAILURE);
    }
    else{
        char buffer[30];
        while((fgets(buffer, 30, x)) != NULL){
        	buffer[strcspn(buffer, "\n")] = 0;
        	add_item(p1, buffer, nr, ptr1);
        }
        
    }
    
    fclose(x);
    
    //Printing lists and bubble sort
    printf("Unsorted list:\n");
    print_list(p1, elNr);
    bubble_sort(ptr1, elNr);
    printf("\nSorted list:\n");
    print_list(p1, elNr);
    
    FILE* y;
    
    y = fopen("newfile.txt", "w");
    
    if (!y) {
        printf("cannot open the file\n");
        free(p1);
        exit(EXIT_FAILURE);
    }
    else{
        for(int i = 0; i<(elNr-1); i++){
        	fwrite(*ptr1[i], sizeof(char), sizeof(*ptr1[i]), y);
        
        }
    }
    
    fclose(y);
    
    //Freeing memory
    for(int i = 0; i<elNr; i++){
    	free(p1[i]);
    }
    free(p1);
    
    return 0;
}

void print_list(char** k, int l){
   
    for(int i = 0; i<l; i++){
        printf("%d: %s\n",i+1, k[i]);
    }
}

void add_item(char** k, char str[], int* l, char*** m){
   
    (*l)++;
   
    *m = realloc(*m, *l * sizeof(char*));
    if(m == NULL){
    	printf("Memmory allocation fail\n");
    	exit(EXIT_FAILURE);
    }
   
    (*m)[*l-1] = malloc(strlen(str) * sizeof(char)+1);    
    strcpy((*m)[*l-1], str);
}

int scmpr(const void* a, const void* b){
	return strcmp((const char*)a, (const char*)b);
}

void bubble_sort(char*** m, int l){
	for (int i=0; i<l-1; i++)
	{
		for (int j=0; j<(l-1); j++)
		{
			if (scmpr((*m)[j], (*m)[j+1]) > 0)
			{
				char* temp = (*m)[j];
				(*m)[j] = (*m)[j+1];
				(*m)[j+1] = temp;
			}
		}
	}
}

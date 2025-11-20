#include<stdio.h>
#include<stdlib.h>
#include<string.h>
void print_list(int list_size, char ls);
void add_item(char *string, int *list_size, char *ls);
void rem_item(char *string, int *list_size, char *ls);
void s_from_file(char **ls, int *list_size);
void swap(int* s1, int* s2);
void b_Sort(char **arr[], int *list_size);
void s_to_file(char **ls, int *list_size);
int main(){
    int elNr=0;
    char list=NULL;
    list=(char)malloc(elNr*sizeof(char*));
    s_from_file(&list, &elNr);
    b_Sort(list,elNr);
    s_to_file(&list, &elNr);
    //print_list(elNr,list);
    free(list);
}
void print_list(int elNr, char ls)
{
    int *i;
    for(i=0; i<elNr; i++)
    {
        printf("%s", ls[i]);
    }
}
void add_item(char *string, int *list_size, char *ls)
{
    list_size++;
    ls=(char**)realloc(ls,(*list_size)*sizeof(char));
    int s_leng=strlen(string);
    ls[*list_size-1]=(char)malloc((s_leng+1)*sizeof(string));
    strcpy(ls[*list_size-1], string);
}
void rem_item(char *string, int *list_size, char *ls)
{
    int index=-1;
    int i;
    for(i=0;i<*list_size;i++)
    {
    if(strcmp(ls[i],string)==NULL)
    {
        index=i;
        break;
    }
    }
    if(index==-1)
    {
        printf("Item doesn't exist\n");
        return 0;
    }
        free(ls[index]);
        for(i=index;i<*list_size-1;i++)
        {
            ls[i]=ls[i+1];
        }
    *list_size--;
    ls=(char)realloc(ls,*list_size*sizeof(char));
    ls[*list_size]=NULL;
}
void s_from_file(char **ls, int *list_size)
{
    FILE*ptr;
    char letter;
    ptr=fopen("ex_file.txt","r");
    if(NULL==ptr)
    {
        printf("No such file found \n");
    }
    do
    {
        letter=fgetc(ptr);
        add_item(letter,list_size,ls);
    }while(letter!=EOF);
    fclose(ptr);
}
void swap(int* s1, int* s2)
{
    int temp = *s1;
    *s1 = *s2;
    *s2 = temp;
}
void b_Sort(char **arr[], int *list_size)
{
    int i,j;
    for(i=0;i<list_size-1;i++)
    {
        for(j=0;j<list_size-i-1;j++)
        {
            if(arr[j]>arr[j+1])
            {
                swap(&arr[j],&arr[j+1]);
            }
        }
    }
}
void s_to_file(char **ls, int *list_size)
{
    FILE *fout;
    if((fout = fopen("answers.txt", "w")) == NULL)
    {
        printf("Cant write strings to file");
        return 0;
    }
    int i;
    for(i=0; i<list_size; i++)
    {
        printf("%s", ls[i]);
    }
    fclose(fout);
}
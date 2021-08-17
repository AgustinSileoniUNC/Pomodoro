## Laboratorio N2
# Repaso Lenguaje C
### SOI - FCEFyN - UNC


## Objetivo
Sentar las bases del lenguaje para poder comprender los temas a presentar durante el curso y poder realizar las prácticas de programación propuestas durante el semestre.


## Duración
Los temas que se enlistan a continuación son básicos de programación. Este laboratorio está diseñado para resolverse entre 6 y 8 horas.


## Actividades
1. C-strings
Realice las siguientes actividades:
   1. Obtener la **memoria ram total**, **libre** y **disponible** en *Megabytes*. A partir del archivo */proc/meminfo*. 
   1. Obtener la memoria *swap* **Ocupada**. A partir del archivo */proc/meminfo*.
   1. Crear un programa en C que imprima información referida al CPU, a partir del archivo */proc/cpuinfo*:
       - Modelo de CPU.
       - Cantidad de cores 
       - Cantidad de thread por cores.

3. Arreglos
Lea el archivo */proc/version* y copie las palabras en un arreglo dinámico.
Luego:
   1. Imprima la lista de palabras en mayúscula. 
   2. libere la memoria que haya alocado.

4. Estructuras

   1. Conteste las siguientes preguntas:
     - ¿Cómo utilizar typedef junto a struct? ¿Para qué sirve? Ejemplo.
     - ¿Qué es packing and padding ?

   2. A partir de los archivos struct.h y lab2.c, asociados a este laboratorio:
     - Genere un binario a partir de dichos archivos
     - Responda las 3 preguntas que aparecen comentadas en el código.
   

<details><summary> Explique a que se debe los tamanios de cada una de las estructuras.</summary>

   Para poder explicar a que se deben los tamaños de cada una de las estructuras tenemos en cuenta 3 cosas, el tipo de dato que almacena, el orden en que lo hace y si se está o no usando padding.

   Padding : Cuando se añaden bytes de memoria vacios entre los datos almacenados para alinear la información en memoria y que está sea de más simple acceso para el sistema.

   ### Tamaño de los disintos tipos de datos utilizados en estas estructuras.

   char → 1 byte

   short int → 2 bytes

   unsigned short int → 2 bytes

   int → 4 bytes

   long unsigned int → 8 bytes

   ### Orden de las estructuras

   BaseData

   - 4 bytes
  
      char
  
      char
  
      null
  
      null

   - 8 bytes
  
      int
  
      int
  
      int
  
      int

  
   - 12 bytes
  
      unsigned short int
  
      unsigned short int
  
      char
  
      null

  
  - 16 bytes
  
      unsigned short int
  
      unsigned short int
  
      char (Pointer)
  
      char (Pointer)

   - 20 bytes
  
      char (Pointer)
  
      char (Pointer)
  
      null
  
      null

   ReorderData

   - 4 bytes
      char
      char
      null
      null

   - 8 bytes
      int
      int
      int
      int

   - 12 bytes
      unsgined short
      unsigned short
      unsigned short
      unsigned short

   - 16 bytes
      char
      char
      char
      char

   Comparando esta estructura con la anterior podemos como ver que a pesar de tener la misma cantidad y tipo de datos, el hecho de haberlos ordenado de tal forma que el padding sea mínimo nos permite ocupar menos espacio en memoria.

   ExtendedData

   - 4 bytes
      long unsigned int
      long unsigned int
      long unsigned int
      long unsigned int

   - 8 bytes
      long unsigned int
      long unsigned int
      long unsigned int
      long unsigned int

   - 12 bytes
      char
      char
      unsigned short int
      unsigned short int

   - 16 bytes
      int
      int
      int
      int

   - 20 bytes
      unsigned short int
      unsigned short int
      unsigned short int
      unsigned short int

   - 24 bytes
      char
      char (Array)
      char (Array)
      char (Array)

   Aquí se agregó un dato, por lo que ocupa bastante más espacio en memoria pero es muy eficiente ya que se acomoda de tal forma que no se necesita ningún padding.

   BaseDataPacked

   - 4 bytes
      char
      char
      int
      int

   - 8 bytes
      int
      int
      unsigned short int
      unsigned short int

   - 12 bytes
      char
      unsgined short int
      unsigned short int
      char (Array)

   - 14 bytes
      char (Array)
      char (Array)
      empty
      empty

   Al definir la estructura BaseDataPacked con el atributo Packed lo que sucede es que no se añaden los espacios vacíos Padding, como se explicó al prinicipio. Si no que en cambio un dato se almacena a continuación de otro aunque quede cortado. Algo que se ve claramente pasa con el dato int.
</details>
   
<details><summary> Explique por que la expresion que calcula limit y limit_aux generan el mismo resultado.</summary>

   Ambas devuelven el mismo resultado ya que están apuntando al mismo espacio en memoria, sin embargo es importante aclarar que no lo están haciendo de la misma forma, para entender esto explicaremos que es lo que hace cada línea.

   ### limit

   ```c
   char *limit = ((char *) &data + sizeof(BaseData)); 
   ```

   En este caso lo que sucede es que a la dirección de la variable data (variable que contiene una estructura de tipo BaseData) le sumamos el tamaño que posee la estructura BaseData.

   ### limit_aux

   ```c
   char *limit_aux =(char *) (&data +1);
   ```

   En este caso en limit_aux almacenamos la dirección de la variable data más el espacio equivalente a 1 ves el tamaño del tipo de dato que almacena data (en este caso es una estructura BaseData).

   ### Conclusión

   Si bien las formas en que  se llega  a tener la dirección de memoria almcenada es distinta, lo que sucede es que ambas variables obtienen el mismo valor.

   Para que quede claro esto no sería así en caso de que el dato almacenado en data fuera de un tamaño distinto a BaseData o que el valor sumado en limit fuera el de una estructura con un tamaño distinto al almacenado en data.  </details>
   
<details><summary>Explique los valores que se muestran en pantalla en cada iteracion del for</summary>

   Para poder explicar las salidas debemos entender primero que es lo que está haciendo el for y luego que son los datos mostrados.

   ```c
   const BaseData data = {
                  .a = 1, 
                  .b = 3, 
                  .x = 15, 
                  .y = 65535, 
                  .c = 128, 
                  .z = -1, 
                  .d = {1,1,1} 
      };
   char *limit = ((char *) &data + sizeof(BaseData));
   int i = 0;
      /* Explique los valores que se muestran en pantalla en cada iteracion del for */
      for (char *c = (char *) &data; c < limit; c++, i++ ){
         printf("byte %02d : 0x%02hhx \n", i, *c);
      }
   ```

   En primer lugar observamos que dentro del **for** se define un puntero **c**, el cual apunta a la variable data. También tenmos que el condicional es que **c > limit**, sabemos por lo estudiado en el punto anterior que **limit** es la direcciónde **c** más el tamaño de una estructura **BaseData**. Y por último al observar que en cada iteración se incrementan en 1 tanto la variable **i** (solo se usa como identificador) como la variable **c,** llegamos a la conclusión de que vamos a recorrer la estructura almacenada en **data** byte por byte.

   Para el siguiente paso colocaremos una salida de consola y estudiaremos que significan cada uno de esos valores.

   En el primer punto pudimos ver como se almacenan los datos en esta estructura, por lo que utilizando este conocimiento los separaremos según el tipo de dato que almacenan para poder entenderlos más fácilmente. 

   ### char → a x = $1_{dec}$ = $01_{hex}$

   byte 00 : 0x01 

   ### char → b = $3_{dec}$ = $03_{hex}$

   byte 01 : 0x03 

   ### padding padding → valor indefinido

   byte 02 : 0x00 

   byte 03 : 0x00 

   ### int → x = $15_{dec}$ = $0000000f_{hex}$

   byte 04 : 0x0f

   byte 05 : 0x00

   byte 06 : 0x00

   byte 07 : 0x00

   ### unsigned short int → y = $65535_{dec}$ = $ffff_{hex}$

   byte 08 : 0xff

   byte 09 : 0xff

   ### char → c = $128_{dec}$ = $80_{hex}$

   byte 10 : 0x80

   ### padding → valor indefinido

   byte 11 : 0xbb

   ### unsigned short in → z = $-1_{dec}$  = $65535_{dec\_unsigned}$ = $ffff_{hex}$

   byte 12 : 0xff

   byte 13 : 0xff

   ### char (Array) → d[0] = $1_{dec}$ = $01_{hex}$

   byte 14 : 0x01

   ### char (Array) → d[1] = $1_{dec}$ = $01_{hex}$

   byte 15 : 0x01

   ### char (Array) → d[2] = $1_{dec}$ = $01_{hex}$

   byte 16 : 0x01

   ### padding → valor indefinido

   byte 17 : 0xfa

   byte 18 : 0x74

   byte 19 : 0x02
   </details>
   
   3. Crear una lista simplemente enlazada. Escribir función que permite agregar un nodo al final de la lista.
lalalala


## ¿Qué entregar?
- Se debe trabajar en GitHub Classroom. Es necesario subir los archivos de código en C y generar y subir un archivo markdown (.md) para las respuestas.
- Se debe compilar utilizando *gcc -Wall -Werror -Pedantic*.
- Aclaración: No subir binarios ni archivos de proyectos (eg: Eclipse). Solo archivos .h y .c.
- Se recomienda ir haciendo git push incrementales al repositorio a medida que se desarrolla el trabajo.




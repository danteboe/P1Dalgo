import random

import string

def generar_caso_prueba(R, C):
    """Genera una matriz de tamaño RxC con números positivos menores a 103 y algunos -1 como celdas malditas"""
    matriz = []
    for _ in range(R):
        fila = []
        for _ in range(C):
            if random.random() < 0.1:  # Probabilidad del 10% de ser -1 (maldición)
                fila.append(-1)
            else:
                fila.append(random.randint(1, 1000))  # Números entre 1 y 1000
        matriz.append(fila)
    return matriz

def escribir_archivo(nombre_archivo, casos_prueba):
    """Escribe todos los casos de prueba en el archivo con el formato adecuado"""
    with open(nombre_archivo, 'w') as f:
        # Primera línea con el número de casos
        f.write(f"{len(casos_prueba)}\n")

        for caso in casos_prueba:
            R, C, matriz = caso
            # Primera línea de cada caso con R y C
            f.write(f"{R} {C}\n")
            # Escribir la matriz
            for fila in matriz:
                f.write(' '.join(map(str, fila)) + '\n')

def generar_casos_de_prueba(numero_casos, nombre_archivo):
    """Genera un archivo con el número dado de casos de prueba"""
    casos_prueba = []
    for _ in range(numero_casos):
        # Generar dimensiones aleatorias para la matriz (R y C < 200)
        R = 199
        C = 199
        matriz = generar_caso_prueba(R, C)
        casos_prueba.append((R, C, matriz))

    # Escribir los casos en el archivo
    escribir_archivo(nombre_archivo, casos_prueba)

for i in range(1):

    res = ''.join(random.choices(string.ascii_letters,
                             k=7))
    numero_casos = 3
    nombre_archivo =  "casosTOP" + str(numero_casos) +'.in'
    generar_casos_de_prueba(numero_casos, nombre_archivo)
    print(f"Archivo '{nombre_archivo}' generado con {numero_casos} casos de prueba.")

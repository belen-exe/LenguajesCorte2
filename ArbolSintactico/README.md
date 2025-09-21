# Árbol Sintáctico 

## Gramática (gra.txt)

Gramática textual con recursión a la izquierda:
```
E → E opsuma T
E → T
T → T opmul F
T → F
F → id
F → num
F → pari E pard
```

Gramática cambiada para eliminar el error de recursión infinita que provoca, para ello se utlizan auxiliares:
```
E -> T Ep
Ep -> opsuma T Ep | vacio
T -> F Tp
Tp -> opmul F Tp | vacio
F -> num
F -> pari E pard
```

## Código

Cargar la gramática del archivo:

<img width="799" height="348" alt="image" src="https://github.com/user-attachments/assets/61e4cffa-ba94-4ef2-a837-f9579ed9b1e8" />

- Lee y separa en **Terminales** y **No Terminales** por medio de ->
- Luego las guarda en un diccionario

<br>

Transformar cada carácter en un token:

<img width="516" height="400" alt="image" src="https://github.com/user-attachments/assets/be72eca4-ed5c-4e5a-9a53-3474c0c2f2cb" />

- Recorre la cadena carácter a carácter y tokeniza

<br>

Estructura:

<img width="667" height="335" alt="image" src="https://github.com/user-attachments/assets/9aaa8d08-c5ed-41e2-81ff-c08afa5f2ce9" />

- Definen diccionario, simbolo, tokens, árbol, nombres de los nodos.
- nuevo_nodo: crea un nodo y devuelve el identificador nX.
<br>

<img width="867" height="521" alt="image" src="https://github.com/user-attachments/assets/5344b42b-5659-4403-9059-ac14a77e4721" />
<img width="656" height="427" alt="image" src="https://github.com/user-attachments/assets/396a6893-0b2e-48bf-8f64-5fea873c34fe" />

- Si simbolo no está en self.gramatica, lo trata como terminal y crea dos nodos.
- Si simbolo es no-terminal, crea un nodo nodo con etiqueta del no-terminal (ej. "E")

<br>

Limpieza:

<img width="882" height="359" alt="image" src="https://github.com/user-attachments/assets/49dd65e3-64da-475f-832d-8f5d4bc53de8" />

- Elimina nodos basura como vacio o los auxiliares, que solo sirven para evitar errores de rescursion, así el árbol se verá más limpio.
- lo escencial para que esto funcione es poner nombres auxiliares con p al final para que el programa los lea en caso de querer cambiar la gramática.

<br>

Mostrar el arbol:

<img width="829" height="579" alt="image" src="https://github.com/user-attachments/assets/18750292-cd7e-4048-a514-69ccb44b770a" />

- Se dibuja el árbol con nerworkX
- Se llama la gramática, pide la cadena y dependiendo de si acepta o no, se imprime el árbol.

## Resulatdos

Para casos en donde networkX no se pueda instalar, crear y activar un entorno virtual para la instalación:

<img width="971" height="196" alt="image" src="https://github.com/user-attachments/assets/c9727986-9414-49bd-b2b1-ddccb719eff2" />

<br>

Ejemplos:

<img width="1639" height="579" alt="image" src="https://github.com/user-attachments/assets/182a0b24-1a91-4ef6-8c5f-55e2da360485" />
<img width="976" height="175" alt="image" src="https://github.com/user-attachments/assets/4e1e1292-5955-41c8-bbea-5d6a55e9230d" />
<img width="1845" height="688" alt="image" src="https://github.com/user-attachments/assets/8005c430-9a4b-4f1a-b8b5-a7ce729fa32f" />
<img width="1845" height="688" alt="image" src="https://github.com/user-attachments/assets/7fea148f-0475-45a5-86fc-fd4f598ddae9" />
<img width="925" height="735" alt="image" src="https://github.com/user-attachments/assets/429a7a39-22d1-4276-9b50-f395498b14b8" />


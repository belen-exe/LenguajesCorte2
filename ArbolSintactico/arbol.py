import networkx as nx
import matplotlib.pyplot as plt

def cargar_gramatica(archivo):
    gramatica = {}
    simbolo_inicial = None
    with open(archivo, "r") as f:
        for linea in f:
            if "->" not in linea:
                continue
            izquierda, derecha = linea.strip().split("->")
            izquierda = izquierda.strip()
            producciones = [p.strip().split() for p in derecha.split("|")]
            if simbolo_inicial is None:
                simbolo_inicial = izquierda
            gramatica.setdefault(izquierda, []).extend(producciones)
    return gramatica, simbolo_inicial

def tokenizar(cadena):
    tokens = []
    for c in cadena:
        if c.isdigit():
            tokens.append(("num", c))
        elif c.isalpha():
            tokens.append(("id", c))
        elif c in "+-":
            tokens.append(("opsuma", c))
        elif c in "*/":
            tokens.append(("opmul", c))
        elif c == "(":
            tokens.append(("pari", c))
        elif c == ")":
            tokens.append(("pard", c))
    return tokens


class Parser:
    def __init__(self, gramatica, simbolo_inicial, tokens):
        self.gramatica = gramatica
        self.simbolo_inicial = simbolo_inicial
        self.tokens = tokens
        self.grafo = nx.DiGraph()
        self.nodo_id = 0

    def nuevo_nodo(self, etiqueta):
        nodo = f"n{self.nodo_id}"
        self.nodo_id += 1
        self.grafo.add_node(nodo, label=etiqueta)
        return nodo

    def parse(self, simbolo, pos):

        if simbolo == "vacio":
            nodo = self.nuevo_nodo("vacio") 
            return True, nodo, pos

        if simbolo not in self.gramatica:  # terminal
            if pos < len(self.tokens) and self.tokens[pos][0] == simbolo:
                tipo, lex = self.tokens[pos]

                # nodo del símbolo de la gramatica ej "num", "opsuma"
                nodo_tipo = self.nuevo_nodo(tipo)

                # nodo con el valor real ej "2", "+", "*"
                nodo_lex = self.nuevo_nodo(lex)

                # conectar tipo con valor para mostrarse en el arbol
                self.grafo.add_edge(nodo_tipo, nodo_lex)

                return True, nodo_tipo, pos + 1
            return False, None, pos


        for produccion in self.gramatica[simbolo]:
            nodo = self.nuevo_nodo(simbolo)
            pos_actual = pos
            hijos = []
            valido = True
            for s in produccion:
                ok, hijo, pos_actual = self.parse(s, pos_actual)
                if not ok:
                    valido = False
                    break
                hijos.append(hijo)
            if valido:
                for h in hijos:
                    self.grafo.add_edge(nodo, h)
                return True, nodo, pos_actual
            self.grafo.remove_node(nodo)
        return False, None, pos
    
    def limpiar_arbol(self):
        etiquetas = nx.get_node_attributes(self.grafo, "label")
        nodos_quitar = []
        for nodo, et in etiquetas.items():
            if et in ("epsilon", "vacio") or et.endswith("p") or "prima" in et:
                nodos_quitar.append(nodo)
        for n in nodos_quitar:
            padres = list(self.grafo.predecessors(n))
            hijos = list(self.grafo.successors(n))
            for p in padres:
                for h in hijos:
                    self.grafo.add_edge(p, h)
            if n in self.grafo:
                self.grafo.remove_node(n)

    def dibujar(self, raiz):
        pos = nx.nx_pydot.graphviz_layout(self.grafo, prog="dot")
        etiquetas = nx.get_node_attributes(self.grafo, "label")
        nx.draw(self.grafo, pos, labels=etiquetas,
                node_color="lightblue", node_size=2000,
                font_size=12, arrows=False)
        plt.show()

if __name__ == "__main__":
    gramatica, simbolo_inicial = cargar_gramatica("gra.txt")
    cadena = input("Ingrese la cadena:\n")
    tokens = tokenizar(cadena)
    print("Tokens:", tokens)

    parser = Parser(gramatica, simbolo_inicial, tokens)
    ok, raiz, pos = parser.parse(simbolo_inicial, 0)

    if ok and pos == len(tokens):
        print("Cadena aceptada")
        parser.limpiar_arbol()
        parser.dibujar(raiz)
    else:
        print("Cadena no aceptada")


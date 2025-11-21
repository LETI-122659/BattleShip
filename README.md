# Autores
Guilherme Chagas (halofmayor é a conta pessoal, na qual pode aparecer neste repo)(122659) 
Guilherme Torres (122607)
Rafael Reis (122616) ( ou rafael, pode aparecer o nome invés do LETI122616, mas remete à mesma pessoa)
Tiago Nobre (122632)

# Coverage(Notas)
Não obtivemos coverage de 100%, uma vez que é influenciado por diversos fatores:
-Presença de dead codes nas classes que herdam características da classe ship;
-Verificações desnecessárias em alguns métodos;
-A classe Tasks, faz com que a média geral de coverage diminua, ficando a 71%. Contudo a verdadeira percentagem do coverage sem o Tasks é de 90,96%, ou seja 91%, o que demosntra que o nosso código está bem testado.
-Foi modificado (comentado) uma linha da classe Ship, acerca do atributo bearing, (assert bearing != null), de modo a poder que uma condição if na classe Caravel fosse lida e testada, de modo a poder obter o máximo de coverage possível, sem modificar e perturbar os coverages das outras classes vizinhas.


# Battleship
Basic academic version of Battleship game to build upon.


Atualizaçao ficheiro .yml




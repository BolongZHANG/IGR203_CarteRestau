# README programme Qt - côté restaurateur

## Page de connexion

A l'ouverture du programme un appui trop rapide sur le bouton
connexion peut faire planter le programme

Pas besoin de login et mot de passe pour se connecter

## Actualisation des commandes

Pour actualiser la liste des commandes il faut appuyer sur
la touche entrée. Les nouvelles tables apparaissent après deux appuis.

Deux appuis très proche peuvent parfois faire planter le programme.

## structure 

La classe principale, MainWindow contient tous les produits du restaurant 
stockés dans la classe Produits et toutes les commandes stockées dans la classe
Commandes. Un Produit est représenté par la classe Produit. Une commande, 
représentée par la classe Commande, contient plusieurs produits stockés sous 
la forme d'identifiants qui serviront à les trouver dans la classe Produits. 
Elle contient aussi des menus représentés par la classe Menu. Les menus contiennent 
des produits.

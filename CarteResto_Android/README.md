# Carte numérique

## Fonction ilplémenté

L'objectif de notre application est de créer une menu numérique pour facliter les clients. Avec notre apps, les clients peuvent voir les détaillé sur tous les plats,  les entrées, les apéro et les dessert. Et aussi, on construit une systeme de sinchronisation des données en temps réel à l'aide de Firebase. Il permet des clients dans la même table de choisir les plats simultanément. Mais il y a encore des bugs à fixer. On aussi crée une interface de panier, les clients peuvent voir tous leur choix.   

## Conception de l'interface

quand on conçois l'interface, on a pensé à pratiquer le material design. On  Vous pouvez contatez que l'on utlise pas mal de float action button et les cardview. 

Notre interfaces est composé par 4 activité principale: LoginActivity, MenuPrincipaleActivity, MenuDetaileActivity, PanierActivity.  

MenuPrincipaleActivity est conposé par trois partie: 

- Toolbar, pour affchier faire le recherche, rappel le serveur et voir le panir.
- Button Groupe , pour changer le catégorie du products.
- Fragment: il affiche le list de produt en fonction du choix de catégorie.

Dans la parite, on utilise différent de méthode pour afficher les plat et les menu. Les plats sont listé par un cardview. Les cilent peuvent changer la quantité directement. Il peut aussi taper le image pour voir le description de plat et changer le quantité. Pour la list des munus(formules), on envisage d'utiliser le "metro design", mais on trouve que c'est très difficile à implémenté, donc   on aussi les montre par cardview. Mais utilisateur ne peut pas changer le quantité directement dans le liste. Il peut taper l'image pour entrer MenuDetailActivity. Dans ce Activity, les clients peut voir les details sur ce menu, changer le quantité et faire meur choix proposé par ce formles.  Dans ce partie, on profite l'animation fournie par android.

Sur la Panier. On crée une liste pour affichir la command. On aussi ajour la liste de recommandation sur cette interface. Mais pour faciliter notre projet, il ne propose que les vins.

## Structure 

Dans notre project, on a tenté de implenter la structure proposé par Google à l'aide de "Android Architecture Components".

![Architecture](final-architecture.png)

Notre programme peut etre divisé en deux partie.

La premier partie est le "data layout". Comme notre groupe envisage de créer un partie sur le PC pour gestion des command de client. Il est nécessaire de syncroniser  les donnée entre  l'apps et la partie du PC. On crée un serveur sur "Firebase ".   Par conséquence, notre application doivent syncroniser les donnée local et les donnée sur serveur.  A l'aide, Les données locals sont mis sur le sqlite. On utilise le "ROOM" pour simplifier les opération sur sqlite. Comme firebase fourit des API très facile à utiliser, on décide d'implémenter la partie du serveur sur Firebase. Afin de synchroniser et fusioner les donnée sur les deux côté, on crée une classe "Repertoire". Il va fournir des API tel que le reste du notre apps peut récuper des donnée facilement. Mais  Comme le temps est limite, on ce partie n'est pa bien implémenté.Tous les images sont enregistrer sur Firebase Storage. Donc chaque fois, quand la liste du plat est mis à jours, notre programmation va retéléchanger ces images.

La deuxième partie est le ui layout. On va conçoire une interface dirigé par les données. Donc pour preque chaque activité et fragment, on crée une ViewModel correspondant. On tenté de utiliser le Livedata pour rendre les donnée observable. Cela prend breacuoup de temps. Comme les partie de "data layout" n'est pas encore  bien implémenter. Quelque Viewmodel procéde la référence sur la base de donnée.  












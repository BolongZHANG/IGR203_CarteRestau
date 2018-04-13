# Carte numérique


## Structure 
Notre programme peut etre divisé en deux partie.
La premier partie est le data layout. La data de notre programme vient de firebase. Mais on aussi crée un base de donnée local comme une cache.
Afin de fusionner et syncroniser les deux partie, on envisage de créer une classe de repertoir. Le repertoir fournie des api. Les reste de nottre programme peut obtenir les donné à partir ces api. Comme le temps est limite, on ce partie n'est pa bien implémenté.

La deuxième partie est le ui layout. On va conçoire une interface dirigé par les données. Donc on construire notre programme à l'aide de  libraire "Android Architecture Components" proposé par Google. Donc pour preque chaque activité et fragment, on crée une ViewModel correspondant. On tenté de utiliser le Livedata pour rendre les donnée observable. Comme les partie de "data layout" n'est pas encore  


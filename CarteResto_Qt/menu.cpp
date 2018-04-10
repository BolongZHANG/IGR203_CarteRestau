#include "menu.h"

#include <QString>
#include <QVector>
#include <QJsonObject>
#include <iostream>

Menu::Menu()
{

}

Menu::Menu( QJsonObject menuJson , QString id )
{
    this->menuJson = menuJson;
    this->id = id;
    previousNbProduits = 0;
    nbProduits = 0;
    setProduits();
    previousNbProduits = nbProduits;
}

void Menu::setProduits()
{
        nbProduits = menuJson.keys().size();

        for( int i = 0 ; i < nbProduits ; ++i)
        {
            QString keyP = menuJson.keys().at(i);

            QString idP = menuJson[keyP].toObject().value("id").toString();
            double quantity = menuJson[keyP].toObject().value("quantity").toDouble();

            int index = containsIdProduit( idP.toDouble() );

            if( index < 0 ) // le produit ne fait pas déja partie du Menu
            {
                idProduits.push_back( idP.toDouble() );
                quantitesP.push_back( quantity );
                stateP.push_back(0);
            }
            else
                quantitesP[ index ] = quantity;

        }

        previousNbProduits = nbProduits;
}

int Menu::containsIdProduit(double id)
{
    for(int i = 0 ; i < previousNbProduits ; ++i)
        if(idProduits.at( i ) == id ) return i;

    return -1;
}

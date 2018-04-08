#include "menu.h"

#include <QString>
#include <QVector>
#include <QJsonObject>
#include <iostream>

Menu::Menu()
{

}

Menu::Menu( QJsonObject menuJson )
{
    this->menuJson = menuJson;
    setProduits();
}

void Menu::setProduits()
{

        nbProduits = menuJson.keys().size();

        for( int i = 0 ; i < nbProduits ; ++i)
        {
            QString keyP = menuJson.keys().at(i);

            QString idP = menuJson[keyP].toObject().value("id").toString();
            double quantity = menuJson[keyP].toObject().value("quantity").toDouble();

            idProduits.push_back( idP.toDouble() );
            quantitesP.push_back( quantity );
        }
}

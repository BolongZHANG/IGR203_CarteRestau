#include "commande.h"
#include "menu.h"

#include <QJsonValue>
#include <QJsonObject>
#include <iostream>
#include <QString>

Commande::Commande()
{

}

Commande::Commande(QJsonValue commandeJson , int table)
{
    this->commandeJson = commandeJson;
    this->table = table;

    nbProduits = 0;
    nbMenus = 0;
    previousNbMenus = 0;
    previousNbProduits = 0;

    setProduits();
    setMenus();

    nbProduits = idProduits.size();
    nbMenus = menusCmd.size();

    previousNbProduits = nbProduits;
    previousNbMenus = nbMenus;
}

void Commande::setId()
{

}

void Commande::setProduits()
{
    QString key("productMap");

    QJsonValue productMap = commandeJson.toObject().value(key);

    if( productMap.isObject() )
    {
        QJsonObject products = productMap.toObject();
        nbProduits = products.keys().size();

        for( int i = 0 ; i < nbProduits ; ++i)
        {
            QString keyP = products.keys().at(i);
            QString idP = products[keyP].toObject().value("id").toString();
            double quantity = products[keyP].toObject().value("quantity").toDouble();

            int index = containsIdProduit( idP.toDouble() );
            if( index < 0 ) // si le produit n'est pas présent
            {
                idProduits.push_back( idP.toDouble() );
                quantitesP.push_back( quantity );
                stateP.push_back(0);
            }
            else // si il est déja présent
                 quantitesP[ index ] = quantity ; // si jamais la quantite a changé
        }

        previousNbProduits = nbProduits;
    }
}

void Commande::setMenus()
{
    QString key("menuMap");

    QJsonValue menuMap = commandeJson.toObject().value(key);

    if( menuMap.isObject() )
    {
        QJsonObject menus = menuMap.toObject();
        nbMenus = menus.keys().size();

        for( int i = 0 ; i < nbMenus ; ++i)
        {
            QString keyM = menus.keys().at(i);

            int index = containsMenu( keyM );

            QJsonObject menu = menus[keyM].toObject();
            QJsonObject dishesList = menu[QString("dishesList")].toObject();

            if( index < 0) // le menu n'est pas présent dans la liste
            {
                menusCmd.push_back( Menu( dishesList , keyM ) );
            }
            else // on met à jour le menu existant
            {
                Menu m = menusCmd[ index ];
                m.update( dishesList );
                menusCmd[ index ] = m;
            }
        }

        previousNbMenus = nbMenus;
    }
}

int Commande::containsIdProduit(double id)
{
    for(int i = 0 ; i < previousNbProduits ; ++i)
        if(idProduits.at( i ) == id ) return i;
    //std::cout << "nouveau produit = " << id << std::endl;
    return -1;
}

int Commande::containsMenu( QString idMenu )
{
    for(int i = 0 ; i < previousNbMenus ; ++i)
        if(menusCmd.at( i ).getId() == idMenu ) return i;
    //std::cout << "nouveau menu = " << idMenu.toStdString() << std::endl;
    return -1;
}

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
    setProduits();
    setMenus();

    nbProduits = idProduits.size();
    nbMenus = menusCmd.size();
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
        int size = products.keys().size();

        for( int i = 0 ; i < size ; ++i)
        {
            QString keyP = products.keys().at(i);
            QString idP = products[keyP].toObject().value("id").toString();
            double quantity = products[keyP].toObject().value("quantity").toDouble();

            idProduits.push_back( idP.toDouble() );
            quantitesP.push_back( quantity );
        }
    }
}

void Commande::setMenus()
{
    QString key("menuMap");

    QJsonValue menuMap = commandeJson.toObject().value(key);

    if( menuMap.isObject() )
    {
        QJsonObject menus = menuMap.toObject();
        int size = menus.keys().size();

        for( int i = 0 ; i < size ; ++i)
        {
            QString keyM = menus.keys().at(i);

            QJsonObject menu = menus[keyM].toObject();
            QJsonObject dishesList = menu[QString("dishesList")].toObject();

            menusCmd.push_back( Menu( dishesList ) );
        }
    }
}

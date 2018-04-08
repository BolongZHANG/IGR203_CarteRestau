#ifndef COMMANDE_H
#define COMMANDE_H

#include "produit.h"
#include "menu.h"

#include <QString>
#include <QVector>
#include <QJsonValue>

class Commande
{
public:
    Commande();
    Commande(QJsonValue commandeJson , int table);

    int getTable() const { return table ; }
    void setId();
    void setProduits();
    void setMenus();

    int getNbProduits() const { return nbProduits; }
    int getNbMenus() const { return nbMenus; }

    double getProduitId( int index ) const { return idProduits.at(index) ; }
    double getProduitQuantity( int index ) const { return quantitesP.at(index) ; }

    Menu getMenu( int index ) const { return menusCmd.at(index) ; }

private:
    int table;
    QString id;
    QVector<double> idProduits;
    QVector<double> quantitesP;

    QVector<Menu> menusCmd;

    QJsonValue commandeJson;

    int nbProduits;
    int nbMenus;
};

#endif // COMMANDE_H

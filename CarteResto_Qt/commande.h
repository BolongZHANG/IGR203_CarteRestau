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
    int getProduitState( int index ) const { return stateP.at(index) ; }

    void setProduitState( int index , int state ) { stateP[index] = state; }

    void setMenuProduitState(int menuId , int index , int state )
    {
        Menu m = menusCmd.at( menuId );
        m.setProduitState( index , state );
        menusCmd[ menuId ] = m;
    }

    Menu getMenu( int index ) const { return menusCmd.at(index) ; }

    void update( QJsonValue commandeJson )
    {
        this->commandeJson = commandeJson;
        setProduits();
        setMenus();
    }

    int containsIdProduit(double id);
    int containsMenu( QString idMenu );

private:
    int table;
    QString id;
    QVector<double> idProduits;
    QVector<double> quantitesP;
    QVector<int> stateP; // red for not launched, orange for launched and green for served

    QVector<Menu> menusCmd;

    QJsonValue commandeJson;

    int nbProduits;
    int nbMenus;

    int previousNbProduits;
    int previousNbMenus;
};

#endif // COMMANDE_H

#ifndef MENU_H
#define MENU_H

#include <QString>
#include <QVector>
#include <QJsonObject>

class Menu
{
public:
    Menu();
    Menu( QJsonObject menuJson , QString id );
    void setProduits();

    int getNbProduits() const { return nbProduits; }

    double getIdProduits( int index ) const { return idProduits.at( index ); }
    double getQuantitesP( int index ) const { return quantitesP.at( index ); }

    QString getId() const { return id; }

    int getProduitState( int index ) const { return stateP.at(index) ; }

    void setProduitState( int index , int state ) { stateP[index] = state; }

    int containsIdProduit(double id);

    void update( QJsonObject menuJson )
    {
        this->menuJson = menuJson;
        setProduits();
    }

private:
    QVector<double> idProduits;
    QVector<double> quantitesP;
    QVector<int> stateP;

    QString id;

    int nbProduits;
    int previousNbProduits;

    QJsonObject menuJson;
};

#endif // MENU_H

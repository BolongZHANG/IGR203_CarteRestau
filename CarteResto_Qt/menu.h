#ifndef MENU_H
#define MENU_H

#include <QString>
#include <QVector>
#include <QJsonObject>

class Menu
{
public:
    Menu();
    Menu( QJsonObject menuJson );
    void setProduits();

    int getNbProduits() const { return nbProduits; }

    double getIdProduits( int index ) const { return idProduits.at( index ); }
    double getQuantitesP( int index ) const { return quantitesP.at( index ); }

private:
    QVector<double> idProduits;
    QVector<double> quantitesP;

    int nbProduits;

    QJsonObject menuJson;
};

#endif // MENU_H

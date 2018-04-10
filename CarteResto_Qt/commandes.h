#ifndef COMMANDES_H
#define COMMANDES_H

#include "Commande.h"

#include <QString>
#include <QObject>
#include <QVector>
#include <QJsonObject>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkAccessManager>

class Commandes : public QObject
{
    Q_OBJECT

public:
    Commandes();
    ~Commandes();
    void retrieveCommandes();
    void getTableNumber();
    QJsonObject ObjectFromString(const QString& in);
    void createCommandes();

    int getNbCommandes() const { return nbCommandes; }
    int getTable( int index ) const { return tables.at(index); }
    Commande getCommande( int index ) const { return commandes.at( index ); }

    void setProduitState(int cmdId , int index , int state )
    {
        Commande c = commandes.at( cmdId );
        c.setProduitState( index , state );
        commandes[ cmdId ] = c;
    }

    void setMenuProduitState(int cmdId , int menuId , int index , int state )
    {
        Commande c = commandes.at( cmdId );
        c.setMenuProduitState( menuId , index , state );
        commandes[ cmdId ] = c;
    }

    int containsCommandeId(QString id);
    int containsTable( int id );

    int findTableIndex( int tableNb )
    {
        for( int i = 0; i < nbCommandes ; ++i)
            if( tables.at(i) == tableNb ) return i;
    }

public slots:
    void onResult(QNetworkReply * reply);
    void onResultTable(QNetworkReply * reply);

private:
    const QString urlString = "https://carterestoandroid.firebaseio.com/Command%20Info.json";
    const QString urlStringTable = "https://carterestoandroid.firebaseio.com/Table%20Info.json";

    QVector<Commande> commandes;
    QVector<QString> commandesId;
    QVector<int> tables;
    int nbCommandes;
    int previousNbCommandes;

    QNetworkAccessManager networkManagerCmds;
    QNetworkAccessManager networkManagerTables;
    QNetworkReply * currentReply;
    QJsonObject commandesJson;
    QJsonObject tablesJson;
};


#endif // COMMANDES_H

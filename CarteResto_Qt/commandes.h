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

    QNetworkAccessManager networkManagerCmds;
    QNetworkAccessManager networkManagerTables;
    QNetworkReply * currentReply;
    QJsonObject commandesJson;
    QJsonObject tablesJson;
};


#endif // COMMANDES_H

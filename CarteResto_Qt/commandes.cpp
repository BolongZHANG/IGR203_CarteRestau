#include "commandes.h"

#include <iostream>
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkRequest>
#include <QtNetwork/QNetworkReply>
#include <QUrl>
#include <QJsonObject>
#include <QJsonDocument>
#include <QJsonValue>

Commandes::Commandes()
{
    currentReply = nullptr;
    nbCommandes = 0;
    previousNbCommandes = 0;
    QObject::connect( &networkManagerCmds, SIGNAL(finished(QNetworkReply*)), this, SLOT( onResult(QNetworkReply*) ) );
    QObject::connect( &networkManagerTables, SIGNAL(finished(QNetworkReply*)), this, SLOT( onResultTable(QNetworkReply*) ) );
}

Commandes::~Commandes()
{
    delete currentReply;
}

void Commandes::retrieveCommandes()
{
    QUrl myurl( urlString );

    std::cout << "Url = "  <<  myurl.toString().toStdString() << std::endl;

    QNetworkRequest request;
    request.setUrl(myurl);
    request.setHeader(QNetworkRequest::ContentTypeHeader,"application/json");

    currentReply = networkManagerCmds.get(request);  // GET
}

void Commandes::getTableNumber()
{
    QUrl myurl( urlStringTable );

    std::cout << "Url = "  <<  myurl.toString().toStdString() << std::endl;

    QNetworkRequest request;
    request.setUrl(myurl);
    request.setHeader(QNetworkRequest::ContentTypeHeader,"application/json");

    currentReply = networkManagerTables.get(request);  // GET
}

void Commandes::onResult(QNetworkReply * reply)
{
    if (currentReply->error() != QNetworkReply::NoError)
    {
        std::cout << "error" << std::endl;
        return;  // ...only in a blog post
    }

    QString data = (QString) reply->readAll();

    //std::cout << "datas:" << data.toStdString() << std::endl;

    commandesJson = ObjectFromString(data);

    nbCommandes = commandesJson.keys().size();
    std::cout << "nbCommandes = " << nbCommandes << std::endl;
    for( int i = 0 ; i < nbCommandes ; ++i) // on récupère les id des commandes
    {
        QString key = commandesJson.keys().at(i);

        if( containsCommandeId( key ) < 0 ) // si la commande n'existe pas on l'ajoute
            commandesId.push_back(key);
    }

    // now that we have tha commandes id we can get the table number
    getTableNumber();
}

void Commandes::onResultTable(QNetworkReply * reply)
{
    if (currentReply->error() != QNetworkReply::NoError)
    {
        std::cout << "error" << std::endl;
        return;  // ...only in a blog post
    }

    QString data = (QString) reply->readAll();

    //std::cout << "datas:" << data.toStdString() << std::endl;

    tablesJson = ObjectFromString(data);

    for( int i = 0 ; i < nbCommandes ; ++i) // on récupère les id des commandes
    {
        QString key = tablesJson.keys().at(i);

        if( containsTable( key.toInt() ) < 0 ) // si la table n'existe pas déja on l'ajoute
            tables.push_back(key.toInt());
    }

    createCommandes();
}

void Commandes::createCommandes()
{

    QString tabNb("tableNb");
    for( int i = 0 ; i < nbCommandes ; ++i)
    {
        QJsonValue jsonCommande = commandesJson[ commandesId.at(i) ];

        int tableNb = jsonCommande.toObject().value(tabNb).toString().toInt();

        int index = containsCommandeId( commandesId.at(i) );
        if( index < 0 ) // on ajoute la nouvelle commande
            commandes.push_back( Commande(jsonCommande , tables.at( findTableIndex( tableNb ) ) ) );
        else // ancienne commande
        {
            Commande c = commandes[ index ];
            c.update( jsonCommande );
            commandes[ index ] = c;
        }
    }

    nbCommandes = commandes.size();
    previousNbCommandes = nbCommandes;
}

QJsonObject Commandes::ObjectFromString(const QString& in)
{
    QJsonObject obj;

    QJsonDocument doc = QJsonDocument::fromJson(in.toUtf8());

    // check validity of the document
    if(!doc.isNull())
    {
        if(doc.isObject())
        {
            obj = doc.object();
        }
        else
        {
            std::cout << "Document is not an object" << std::endl;
        }
    }
    else
    {
        std::cout << "Invalid JSON...\n" << in.toStdString() << std::endl;
    }

    return obj;
}

int Commandes::containsCommandeId( QString id )
{
    for( int i = 0 ; i < previousNbCommandes ; ++i )
        if( id == commandesId.at(i) ) return i;
    //std::cout << "nouvelle commande = " << id.toStdString() << std::endl;
    return -1;
}

int Commandes::containsTable( int id )
{
    for( int i = 0 ; i < previousNbCommandes ; ++i )
        if( id == tables.at(i) ) return i;
    //std::cout << "nouvelle table = " << id << std::endl;
    return -1;
}

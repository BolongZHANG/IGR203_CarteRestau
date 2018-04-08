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

    for( int i = 0 ; i < nbCommandes ; ++i) // on récupère les id des commandes
    {
        QString key = commandesJson.keys().at(i);
        commandesId.push_back(key);
    }

    /*QString key = commandesJson.keys().at(0);
    QString idKey = "productMap";
    std::cout << key.toStdString() << std::endl;
    QJsonValue qj = commandesJson[key];
    std::cout << qj.toObject().value( idKey.toLatin1() ).toString().toStdString() << std::endl;*/

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
        tables.push_back(key.toInt());
    }

    createCommandes();
}

void Commandes::createCommandes()
{
    for( int i = 0 ; i < nbCommandes ; ++i)
    {
        QJsonValue jsonCommande = commandesJson[ commandesId.at(i) ];
        commandes.push_back( Commande(jsonCommande , tables.at(i) ) );
    }
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

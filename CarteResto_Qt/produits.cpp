#include "produits.h"
#include "produit.h"

#include <iostream>
#include <QString>
#include <QStringList>
#include <QObject>
#include <QJsonObject>
#include <QJsonValue>
#include <QJsonDocument>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkAccessManager>

Produits::Produits()
{
    currentReply = nullptr;
    QObject::connect( &networkManager, SIGNAL(finished(QNetworkReply*)), this, SLOT( onResult(QNetworkReply*) ) );
}

Produits::~Produits()
{
    delete currentReply;
}

void Produits::retrieveProducts()
{
    QUrl myurl( urlString );

    std::cout << "Url = "  <<  myurl.toString().toStdString() << std::endl;

    QNetworkRequest request;
    request.setUrl(myurl);
    request.setHeader(QNetworkRequest::ContentTypeHeader,"application/json");

    currentReply = networkManager.get(request);  // GET
}

void Produits::onResult(QNetworkReply * reply)
{
    if (currentReply->error() != QNetworkReply::NoError)
    {
        std::cout << "error" << std::endl;
        return;  // ...only in a blog post
    }

    QString data = (QString) reply->readAll();

    data.remove(0,1);
    data.remove(data.size() - 1, 1);

    data = makeJson(data);

    produitsJson = ObjectFromString(data);

    std::cout << "Json size = " << produitsJson.size() << std::endl;
    for( int i = 0 ; i < nbProducts ; ++i)
    {
        QString description = getDescription( i );
        double id = getId( i );
        QString ingredients = getIngredient( i );
        QString name = getName( i );
        double price = getPrice( i );
        QString type = getType( i );
        produits.push_back( Produit(description,id,ingredients,name,price,type) );
    }
}

QString Produits::getDescription( int keyIndex )
{
    QString key = produitsJson.keys().at( keyIndex );
    QString idKey = "description";
    QJsonValue qj = produitsJson[key];
    return qj.toObject().value( idKey.toLatin1() ).toString();
}

double Produits::getId( int keyIndex )
{
    QString key = produitsJson.keys().at( keyIndex ); // produit keyIndex (dans la list)
    QString idKey = "id";
    QJsonValue qj = produitsJson[key]; // json contenant description, ... , type
    QJsonValue qj2 = qj.toObject().value(idKey); // on le retransforme en Json et on récupère la valeur à la clef "id"
    return qj2.toDouble();
}

QString Produits::getIngredient( int keyIndex )
{
    QString key = produitsJson.keys().at( keyIndex );
    QString idKey = "ingredients";
    QJsonValue qj = produitsJson[key];
    return qj.toObject().value( idKey.toLatin1() ).toString();
}

QString Produits::getName( int keyIndex )
{
    QString key = produitsJson.keys().at( keyIndex );
    QString idKey = "name";
    QJsonValue qj = produitsJson[key];
    return qj.toObject().value( idKey.toLatin1() ).toString();
}

double Produits::getPrice( int keyIndex )
{
    QString key = produitsJson.keys().at( keyIndex ); // produit i (dans la list)
    QString idKey = "price";
    QJsonValue qj = produitsJson[key]; // json contenant description, ... , type
    QJsonValue qj2 = qj.toObject().value(idKey); // on le retransforme en Json et on récupère la valeur à la clef "id"
    return qj2.toDouble();
}

QString Produits::getType( int keyIndex )
{
    QString key = produitsJson.keys().at( keyIndex );
    QString idKey = "type";
    QJsonValue qj = produitsJson[key];
    return qj.toObject().value( idKey.toLatin1() ).toString();
}

QString Produits::makeJson(QString & data)
{
    QStringList qsl = data.split("{");

    // because we get a "" at the beginning and at the end

    qsl.removeAt(0);
    qsl.removeAt(qsl.size() - 1);

    nbProducts = qsl.size();

    std::cout << nbProducts << std::endl;

    QString first = QString::fromStdString("{\"");
    QString second = QString::number(0);
    QString third = QString::fromStdString("\":{");

    QString s = first + second + third + qsl.at(0);

    qsl.replace(0,s);

    for( int i = 1 ; i < nbProducts - 1 ; ++i)
    {
        QString f= QString::fromStdString("\"");
        QString s = QString::number(i);
        QString t = QString::fromStdString("\":{");

        QString sf = f + s + t + qsl.at(i);

        qsl.replace(i , sf);
    }

    first = QString::fromStdString("\"");
    second = QString::number(nbProducts - 1);
    third = QString::fromStdString("\":{");
    QString fourth = QString::fromStdString("}");

    s = first + second + third + qsl.at(nbProducts - 1);
    s = s.remove( s.size() - 1 , 1);
    s = s + fourth;

    qsl.replace(nbProducts - 1 , s);

    QString final = qsl.at(0);

    for(int i = 1 ; i < nbProducts ; ++i)
        final = final + qsl.at(i);

    return final;
}

QJsonObject Produits::ObjectFromString(const QString& in)
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
        std::cout << "Invalid JSON...\n" << std::endl; //in.toStdString() << std::endl;
    }

    return obj;
}

Produit Produits::findProduit( double id )
{
    for( int i = 0 ; i < nbProducts ; ++i )
    {
        if(produits[i].getId() == id)
            return produits.at(i);
    }

    return Produit();
}



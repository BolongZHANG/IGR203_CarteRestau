#include "produit.h"

#include <QString>
#include <QObject>
#include <QJsonObject>
#include <QJsonValue>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkAccessManager>

Produit::Produit()
{
    type = "notFound";
}

Produit::Produit(QString description, double id, QString ingredients, QString name, double price, QString type)
{
    this->description = description;
    this->id = id;
    this->ingredients = ingredients;
    this->name = name;
    this->price = price;
    this->type = type;
}


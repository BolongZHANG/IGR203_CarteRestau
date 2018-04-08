#ifndef PRODUITS_H
#define PRODUITS_H

#include "produit.h"

#include <QObject>
#include <QJsonObject>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkAccessManager>

class Produits : public QObject
{
    Q_OBJECT

public:
    Produits();
    ~Produits();

    void retrieveProducts();
    QJsonObject ObjectFromString(const QString& in);
    QString makeJson(QString & data);

    QString getDescription( int keyIndex );
    double getId( int keyIndex );
    QString getIngredient( int keyIndex );
    QString getName( int keyIndex );
    double getPrice( int keyIndex );
    QString getType( int keyIndex );

    Produit findProduit( double id );

public slots:
    void onResult(QNetworkReply * reply);

private:
    const QString urlString = "https://carterestoandroid.firebaseio.com/Products%20Info.json";

    int nbProducts;

    QVector<Produit> produits;
    QNetworkAccessManager networkManager;
    QNetworkReply * currentReply;
    QJsonObject produitsJson;
};

#endif // PRODUITS_H

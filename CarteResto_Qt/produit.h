#ifndef PRODUIT_H
#define PRODUIT_H

#include <QString>

class Produit
{

public:
    Produit();
    Produit(QString description, double id, QString ingredients, QString name, double price, QString type);

    QString getType() const { return type; }
    double getId() const { return id ; }
    QString getName() const { return name; }

private:
    QString description;
    double id;
    QString ingredients;
    QString name;
    double price;
    QString type;
};

#endif // PRODUIT_H

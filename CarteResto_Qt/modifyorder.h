#ifndef MODIFYORDER_H
#define MODIFYORDER_H

#include <QDialog>

namespace Ui {
class ModifyOrder;
}

class ModifyOrder : public QDialog
{
    Q_OBJECT

public:
    explicit ModifyOrder(QWidget *parent = 0);
    ~ModifyOrder();

private:
    Ui::ModifyOrder *ui;
};

#endif // MODIFYORDER_H

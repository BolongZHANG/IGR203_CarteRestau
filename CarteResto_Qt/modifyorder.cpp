#include "modifyorder.h"
#include "ui_modifyorder.h"

ModifyOrder::ModifyOrder(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ModifyOrder)
{
    ui->setupUi(this);
}

ModifyOrder::~ModifyOrder()
{
    delete ui;
}

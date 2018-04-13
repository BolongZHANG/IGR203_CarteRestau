/********************************************************************************
** Form generated from reading UI file 'modifyorder.ui'
**
** Created by: Qt User Interface Compiler version 5.10.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MODIFYORDER_H
#define UI_MODIFYORDER_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QComboBox>
#include <QtWidgets/QDialog>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTabWidget>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ModifyOrder
{
public:
    QGridLayout *gridLayout;
    QTabWidget *modifyOrderTabs;
    QWidget *aperoTab;
    QGridLayout *gridLayout_19;
    QWidget *verticalWidget_9;
    QVBoxLayout *verticalLayout_17;
    QWidget *horizontalWidget_9;
    QHBoxLayout *horizontalLayout_9;
    QListWidget *listWidgetEntree_9;
    QWidget *verticalWidget_10;
    QGridLayout *gridLayout_18;
    QPushButton *addButton1_3;
    QPushButton *delButton1_3;
    QWidget *verticalWidget_27;
    QVBoxLayout *verticalLayout_18;
    QComboBox *comboBox_9;
    QListWidget *listWidget_9;
    QPushButton *validerButton1_3;
    QWidget *entreesTab;
    QGridLayout *gridLayout_3;
    QWidget *verticalWidget;
    QVBoxLayout *verticalLayout;
    QWidget *horizontalWidget;
    QHBoxLayout *horizontalLayout;
    QListWidget *listWidgetEntree;
    QWidget *verticalWidget_2;
    QGridLayout *gridLayout_2;
    QPushButton *addButton1;
    QPushButton *delButton1;
    QWidget *verticalWidget_18;
    QVBoxLayout *verticalLayout_2;
    QComboBox *comboBox;
    QListWidget *listWidget;
    QPushButton *validerButton1;
    QWidget *platsTab;
    QGridLayout *gridLayout_8;
    QWidget *verticalWidget_3;
    QVBoxLayout *verticalLayout_3;
    QWidget *horizontalWidget_2;
    QHBoxLayout *horizontalLayout_2;
    QListWidget *listWidgetEntree_2;
    QWidget *verticalWidget_4;
    QGridLayout *gridLayout_4;
    QPushButton *addButton2;
    QPushButton *delButton2;
    QWidget *verticalWidget_11;
    QVBoxLayout *verticalLayout_4;
    QComboBox *comboBox_2;
    QListWidget *listWidget_2;
    QPushButton *validerButton2;
    QWidget *dessertsTab;
    QGridLayout *gridLayout_10;
    QWidget *verticalWidget_12;
    QVBoxLayout *verticalLayout_9;
    QWidget *horizontalWidget_5;
    QHBoxLayout *horizontalLayout_5;
    QListWidget *listWidgetEntree_5;
    QWidget *verticalWidget_13;
    QGridLayout *gridLayout_9;
    QPushButton *addButton3;
    QPushButton *delButton3;
    QWidget *verticalWidget_14;
    QVBoxLayout *verticalLayout_10;
    QComboBox *comboBox_5;
    QListWidget *listWidget_5;
    QPushButton *validerButton3;
    QWidget *boissonsTab;
    QGridLayout *gridLayout_12;
    QWidget *verticalWidget_15;
    QVBoxLayout *verticalLayout_11;
    QWidget *horizontalWidget_6;
    QHBoxLayout *horizontalLayout_6;
    QListWidget *listWidgetEntree_6;
    QWidget *verticalWidget_16;
    QGridLayout *gridLayout_11;
    QPushButton *addButton4;
    QPushButton *delButton4;
    QWidget *verticalWidget_17;
    QVBoxLayout *verticalLayout_12;
    QComboBox *comboBox_6;
    QListWidget *listWidget_6;
    QPushButton *validerButton4;

    void setupUi(QDialog *ModifyOrder)
    {
        if (ModifyOrder->objectName().isEmpty())
            ModifyOrder->setObjectName(QStringLiteral("ModifyOrder"));
        ModifyOrder->resize(959, 625);
        gridLayout = new QGridLayout(ModifyOrder);
        gridLayout->setObjectName(QStringLiteral("gridLayout"));
        modifyOrderTabs = new QTabWidget(ModifyOrder);
        modifyOrderTabs->setObjectName(QStringLiteral("modifyOrderTabs"));
        aperoTab = new QWidget();
        aperoTab->setObjectName(QStringLiteral("aperoTab"));
        gridLayout_19 = new QGridLayout(aperoTab);
        gridLayout_19->setObjectName(QStringLiteral("gridLayout_19"));
        verticalWidget_9 = new QWidget(aperoTab);
        verticalWidget_9->setObjectName(QStringLiteral("verticalWidget_9"));
        verticalLayout_17 = new QVBoxLayout(verticalWidget_9);
        verticalLayout_17->setObjectName(QStringLiteral("verticalLayout_17"));
        horizontalWidget_9 = new QWidget(verticalWidget_9);
        horizontalWidget_9->setObjectName(QStringLiteral("horizontalWidget_9"));
        horizontalLayout_9 = new QHBoxLayout(horizontalWidget_9);
        horizontalLayout_9->setObjectName(QStringLiteral("horizontalLayout_9"));
        listWidgetEntree_9 = new QListWidget(horizontalWidget_9);
        listWidgetEntree_9->setObjectName(QStringLiteral("listWidgetEntree_9"));
        listWidgetEntree_9->setMaximumSize(QSize(16777215, 300));

        horizontalLayout_9->addWidget(listWidgetEntree_9);

        verticalWidget_10 = new QWidget(horizontalWidget_9);
        verticalWidget_10->setObjectName(QStringLiteral("verticalWidget_10"));
        verticalWidget_10->setMaximumSize(QSize(16777215, 300));
        gridLayout_18 = new QGridLayout(verticalWidget_10);
        gridLayout_18->setObjectName(QStringLiteral("gridLayout_18"));
        addButton1_3 = new QPushButton(verticalWidget_10);
        addButton1_3->setObjectName(QStringLiteral("addButton1_3"));

        gridLayout_18->addWidget(addButton1_3, 1, 0, 1, 1);

        delButton1_3 = new QPushButton(verticalWidget_10);
        delButton1_3->setObjectName(QStringLiteral("delButton1_3"));
        QSizePolicy sizePolicy(QSizePolicy::Preferred, QSizePolicy::Preferred);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(delButton1_3->sizePolicy().hasHeightForWidth());
        delButton1_3->setSizePolicy(sizePolicy);

        gridLayout_18->addWidget(delButton1_3, 0, 0, 1, 1);

        verticalWidget_27 = new QWidget(verticalWidget_10);
        verticalWidget_27->setObjectName(QStringLiteral("verticalWidget_27"));
        sizePolicy.setHeightForWidth(verticalWidget_27->sizePolicy().hasHeightForWidth());
        verticalWidget_27->setSizePolicy(sizePolicy);
        verticalWidget_27->setMinimumSize(QSize(250, 0));
        verticalWidget_27->setMaximumSize(QSize(300, 625));
        verticalLayout_18 = new QVBoxLayout(verticalWidget_27);
        verticalLayout_18->setObjectName(QStringLiteral("verticalLayout_18"));
        comboBox_9 = new QComboBox(verticalWidget_27);
        comboBox_9->setObjectName(QStringLiteral("comboBox_9"));

        verticalLayout_18->addWidget(comboBox_9);

        listWidget_9 = new QListWidget(verticalWidget_27);
        listWidget_9->setObjectName(QStringLiteral("listWidget_9"));

        verticalLayout_18->addWidget(listWidget_9);


        gridLayout_18->addWidget(verticalWidget_27, 2, 0, 1, 1);


        horizontalLayout_9->addWidget(verticalWidget_10);


        verticalLayout_17->addWidget(horizontalWidget_9);

        validerButton1_3 = new QPushButton(verticalWidget_9);
        validerButton1_3->setObjectName(QStringLiteral("validerButton1_3"));

        verticalLayout_17->addWidget(validerButton1_3, 0, Qt::AlignRight);


        gridLayout_19->addWidget(verticalWidget_9, 0, 0, 1, 1);

        modifyOrderTabs->addTab(aperoTab, QString());
        entreesTab = new QWidget();
        entreesTab->setObjectName(QStringLiteral("entreesTab"));
        gridLayout_3 = new QGridLayout(entreesTab);
        gridLayout_3->setObjectName(QStringLiteral("gridLayout_3"));
        verticalWidget = new QWidget(entreesTab);
        verticalWidget->setObjectName(QStringLiteral("verticalWidget"));
        verticalLayout = new QVBoxLayout(verticalWidget);
        verticalLayout->setObjectName(QStringLiteral("verticalLayout"));
        horizontalWidget = new QWidget(verticalWidget);
        horizontalWidget->setObjectName(QStringLiteral("horizontalWidget"));
        horizontalLayout = new QHBoxLayout(horizontalWidget);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        listWidgetEntree = new QListWidget(horizontalWidget);
        listWidgetEntree->setObjectName(QStringLiteral("listWidgetEntree"));
        listWidgetEntree->setMaximumSize(QSize(16777215, 300));

        horizontalLayout->addWidget(listWidgetEntree);

        verticalWidget_2 = new QWidget(horizontalWidget);
        verticalWidget_2->setObjectName(QStringLiteral("verticalWidget_2"));
        verticalWidget_2->setMaximumSize(QSize(16777215, 300));
        gridLayout_2 = new QGridLayout(verticalWidget_2);
        gridLayout_2->setObjectName(QStringLiteral("gridLayout_2"));
        addButton1 = new QPushButton(verticalWidget_2);
        addButton1->setObjectName(QStringLiteral("addButton1"));

        gridLayout_2->addWidget(addButton1, 1, 0, 1, 1);

        delButton1 = new QPushButton(verticalWidget_2);
        delButton1->setObjectName(QStringLiteral("delButton1"));
        sizePolicy.setHeightForWidth(delButton1->sizePolicy().hasHeightForWidth());
        delButton1->setSizePolicy(sizePolicy);

        gridLayout_2->addWidget(delButton1, 0, 0, 1, 1);

        verticalWidget_18 = new QWidget(verticalWidget_2);
        verticalWidget_18->setObjectName(QStringLiteral("verticalWidget_18"));
        sizePolicy.setHeightForWidth(verticalWidget_18->sizePolicy().hasHeightForWidth());
        verticalWidget_18->setSizePolicy(sizePolicy);
        verticalWidget_18->setMinimumSize(QSize(250, 0));
        verticalWidget_18->setMaximumSize(QSize(300, 625));
        verticalLayout_2 = new QVBoxLayout(verticalWidget_18);
        verticalLayout_2->setObjectName(QStringLiteral("verticalLayout_2"));
        comboBox = new QComboBox(verticalWidget_18);
        comboBox->setObjectName(QStringLiteral("comboBox"));

        verticalLayout_2->addWidget(comboBox);

        listWidget = new QListWidget(verticalWidget_18);
        listWidget->setObjectName(QStringLiteral("listWidget"));

        verticalLayout_2->addWidget(listWidget);


        gridLayout_2->addWidget(verticalWidget_18, 2, 0, 1, 1);


        horizontalLayout->addWidget(verticalWidget_2);


        verticalLayout->addWidget(horizontalWidget);

        validerButton1 = new QPushButton(verticalWidget);
        validerButton1->setObjectName(QStringLiteral("validerButton1"));

        verticalLayout->addWidget(validerButton1, 0, Qt::AlignRight);


        gridLayout_3->addWidget(verticalWidget, 0, 0, 1, 1);

        modifyOrderTabs->addTab(entreesTab, QString());
        platsTab = new QWidget();
        platsTab->setObjectName(QStringLiteral("platsTab"));
        gridLayout_8 = new QGridLayout(platsTab);
        gridLayout_8->setObjectName(QStringLiteral("gridLayout_8"));
        verticalWidget_3 = new QWidget(platsTab);
        verticalWidget_3->setObjectName(QStringLiteral("verticalWidget_3"));
        verticalLayout_3 = new QVBoxLayout(verticalWidget_3);
        verticalLayout_3->setObjectName(QStringLiteral("verticalLayout_3"));
        horizontalWidget_2 = new QWidget(verticalWidget_3);
        horizontalWidget_2->setObjectName(QStringLiteral("horizontalWidget_2"));
        horizontalLayout_2 = new QHBoxLayout(horizontalWidget_2);
        horizontalLayout_2->setObjectName(QStringLiteral("horizontalLayout_2"));
        listWidgetEntree_2 = new QListWidget(horizontalWidget_2);
        listWidgetEntree_2->setObjectName(QStringLiteral("listWidgetEntree_2"));
        listWidgetEntree_2->setMaximumSize(QSize(16777215, 300));

        horizontalLayout_2->addWidget(listWidgetEntree_2);

        verticalWidget_4 = new QWidget(horizontalWidget_2);
        verticalWidget_4->setObjectName(QStringLiteral("verticalWidget_4"));
        verticalWidget_4->setMaximumSize(QSize(16777215, 300));
        gridLayout_4 = new QGridLayout(verticalWidget_4);
        gridLayout_4->setObjectName(QStringLiteral("gridLayout_4"));
        addButton2 = new QPushButton(verticalWidget_4);
        addButton2->setObjectName(QStringLiteral("addButton2"));

        gridLayout_4->addWidget(addButton2, 1, 0, 1, 1);

        delButton2 = new QPushButton(verticalWidget_4);
        delButton2->setObjectName(QStringLiteral("delButton2"));
        sizePolicy.setHeightForWidth(delButton2->sizePolicy().hasHeightForWidth());
        delButton2->setSizePolicy(sizePolicy);

        gridLayout_4->addWidget(delButton2, 0, 0, 1, 1);

        verticalWidget_11 = new QWidget(verticalWidget_4);
        verticalWidget_11->setObjectName(QStringLiteral("verticalWidget_11"));
        sizePolicy.setHeightForWidth(verticalWidget_11->sizePolicy().hasHeightForWidth());
        verticalWidget_11->setSizePolicy(sizePolicy);
        verticalWidget_11->setMinimumSize(QSize(250, 0));
        verticalWidget_11->setMaximumSize(QSize(300, 625));
        verticalLayout_4 = new QVBoxLayout(verticalWidget_11);
        verticalLayout_4->setObjectName(QStringLiteral("verticalLayout_4"));
        comboBox_2 = new QComboBox(verticalWidget_11);
        comboBox_2->setObjectName(QStringLiteral("comboBox_2"));

        verticalLayout_4->addWidget(comboBox_2);

        listWidget_2 = new QListWidget(verticalWidget_11);
        listWidget_2->setObjectName(QStringLiteral("listWidget_2"));

        verticalLayout_4->addWidget(listWidget_2);


        gridLayout_4->addWidget(verticalWidget_11, 2, 0, 1, 1);


        horizontalLayout_2->addWidget(verticalWidget_4);


        verticalLayout_3->addWidget(horizontalWidget_2);

        validerButton2 = new QPushButton(verticalWidget_3);
        validerButton2->setObjectName(QStringLiteral("validerButton2"));

        verticalLayout_3->addWidget(validerButton2, 0, Qt::AlignRight);


        gridLayout_8->addWidget(verticalWidget_3, 0, 0, 1, 1);

        modifyOrderTabs->addTab(platsTab, QString());
        dessertsTab = new QWidget();
        dessertsTab->setObjectName(QStringLiteral("dessertsTab"));
        gridLayout_10 = new QGridLayout(dessertsTab);
        gridLayout_10->setObjectName(QStringLiteral("gridLayout_10"));
        verticalWidget_12 = new QWidget(dessertsTab);
        verticalWidget_12->setObjectName(QStringLiteral("verticalWidget_12"));
        verticalLayout_9 = new QVBoxLayout(verticalWidget_12);
        verticalLayout_9->setObjectName(QStringLiteral("verticalLayout_9"));
        horizontalWidget_5 = new QWidget(verticalWidget_12);
        horizontalWidget_5->setObjectName(QStringLiteral("horizontalWidget_5"));
        horizontalLayout_5 = new QHBoxLayout(horizontalWidget_5);
        horizontalLayout_5->setObjectName(QStringLiteral("horizontalLayout_5"));
        listWidgetEntree_5 = new QListWidget(horizontalWidget_5);
        listWidgetEntree_5->setObjectName(QStringLiteral("listWidgetEntree_5"));
        listWidgetEntree_5->setMaximumSize(QSize(16777215, 300));

        horizontalLayout_5->addWidget(listWidgetEntree_5);

        verticalWidget_13 = new QWidget(horizontalWidget_5);
        verticalWidget_13->setObjectName(QStringLiteral("verticalWidget_13"));
        verticalWidget_13->setMaximumSize(QSize(16777215, 300));
        gridLayout_9 = new QGridLayout(verticalWidget_13);
        gridLayout_9->setObjectName(QStringLiteral("gridLayout_9"));
        addButton3 = new QPushButton(verticalWidget_13);
        addButton3->setObjectName(QStringLiteral("addButton3"));

        gridLayout_9->addWidget(addButton3, 1, 0, 1, 1);

        delButton3 = new QPushButton(verticalWidget_13);
        delButton3->setObjectName(QStringLiteral("delButton3"));
        sizePolicy.setHeightForWidth(delButton3->sizePolicy().hasHeightForWidth());
        delButton3->setSizePolicy(sizePolicy);

        gridLayout_9->addWidget(delButton3, 0, 0, 1, 1);

        verticalWidget_14 = new QWidget(verticalWidget_13);
        verticalWidget_14->setObjectName(QStringLiteral("verticalWidget_14"));
        sizePolicy.setHeightForWidth(verticalWidget_14->sizePolicy().hasHeightForWidth());
        verticalWidget_14->setSizePolicy(sizePolicy);
        verticalWidget_14->setMinimumSize(QSize(250, 0));
        verticalWidget_14->setMaximumSize(QSize(300, 625));
        verticalLayout_10 = new QVBoxLayout(verticalWidget_14);
        verticalLayout_10->setObjectName(QStringLiteral("verticalLayout_10"));
        comboBox_5 = new QComboBox(verticalWidget_14);
        comboBox_5->setObjectName(QStringLiteral("comboBox_5"));

        verticalLayout_10->addWidget(comboBox_5);

        listWidget_5 = new QListWidget(verticalWidget_14);
        listWidget_5->setObjectName(QStringLiteral("listWidget_5"));

        verticalLayout_10->addWidget(listWidget_5);


        gridLayout_9->addWidget(verticalWidget_14, 2, 0, 1, 1);


        horizontalLayout_5->addWidget(verticalWidget_13);


        verticalLayout_9->addWidget(horizontalWidget_5);

        validerButton3 = new QPushButton(verticalWidget_12);
        validerButton3->setObjectName(QStringLiteral("validerButton3"));

        verticalLayout_9->addWidget(validerButton3, 0, Qt::AlignRight);


        gridLayout_10->addWidget(verticalWidget_12, 0, 0, 1, 1);

        modifyOrderTabs->addTab(dessertsTab, QString());
        boissonsTab = new QWidget();
        boissonsTab->setObjectName(QStringLiteral("boissonsTab"));
        gridLayout_12 = new QGridLayout(boissonsTab);
        gridLayout_12->setObjectName(QStringLiteral("gridLayout_12"));
        verticalWidget_15 = new QWidget(boissonsTab);
        verticalWidget_15->setObjectName(QStringLiteral("verticalWidget_15"));
        verticalLayout_11 = new QVBoxLayout(verticalWidget_15);
        verticalLayout_11->setObjectName(QStringLiteral("verticalLayout_11"));
        horizontalWidget_6 = new QWidget(verticalWidget_15);
        horizontalWidget_6->setObjectName(QStringLiteral("horizontalWidget_6"));
        horizontalLayout_6 = new QHBoxLayout(horizontalWidget_6);
        horizontalLayout_6->setObjectName(QStringLiteral("horizontalLayout_6"));
        listWidgetEntree_6 = new QListWidget(horizontalWidget_6);
        listWidgetEntree_6->setObjectName(QStringLiteral("listWidgetEntree_6"));
        listWidgetEntree_6->setMaximumSize(QSize(16777215, 300));

        horizontalLayout_6->addWidget(listWidgetEntree_6);

        verticalWidget_16 = new QWidget(horizontalWidget_6);
        verticalWidget_16->setObjectName(QStringLiteral("verticalWidget_16"));
        verticalWidget_16->setMaximumSize(QSize(16777215, 300));
        gridLayout_11 = new QGridLayout(verticalWidget_16);
        gridLayout_11->setObjectName(QStringLiteral("gridLayout_11"));
        addButton4 = new QPushButton(verticalWidget_16);
        addButton4->setObjectName(QStringLiteral("addButton4"));

        gridLayout_11->addWidget(addButton4, 1, 0, 1, 1);

        delButton4 = new QPushButton(verticalWidget_16);
        delButton4->setObjectName(QStringLiteral("delButton4"));
        sizePolicy.setHeightForWidth(delButton4->sizePolicy().hasHeightForWidth());
        delButton4->setSizePolicy(sizePolicy);

        gridLayout_11->addWidget(delButton4, 0, 0, 1, 1);

        verticalWidget_17 = new QWidget(verticalWidget_16);
        verticalWidget_17->setObjectName(QStringLiteral("verticalWidget_17"));
        sizePolicy.setHeightForWidth(verticalWidget_17->sizePolicy().hasHeightForWidth());
        verticalWidget_17->setSizePolicy(sizePolicy);
        verticalWidget_17->setMinimumSize(QSize(250, 0));
        verticalWidget_17->setMaximumSize(QSize(300, 625));
        verticalLayout_12 = new QVBoxLayout(verticalWidget_17);
        verticalLayout_12->setObjectName(QStringLiteral("verticalLayout_12"));
        comboBox_6 = new QComboBox(verticalWidget_17);
        comboBox_6->setObjectName(QStringLiteral("comboBox_6"));

        verticalLayout_12->addWidget(comboBox_6);

        listWidget_6 = new QListWidget(verticalWidget_17);
        listWidget_6->setObjectName(QStringLiteral("listWidget_6"));

        verticalLayout_12->addWidget(listWidget_6);


        gridLayout_11->addWidget(verticalWidget_17, 2, 0, 1, 1);


        horizontalLayout_6->addWidget(verticalWidget_16);


        verticalLayout_11->addWidget(horizontalWidget_6);

        validerButton4 = new QPushButton(verticalWidget_15);
        validerButton4->setObjectName(QStringLiteral("validerButton4"));

        verticalLayout_11->addWidget(validerButton4, 0, Qt::AlignRight);


        gridLayout_12->addWidget(verticalWidget_15, 0, 0, 1, 1);

        modifyOrderTabs->addTab(boissonsTab, QString());

        gridLayout->addWidget(modifyOrderTabs, 0, 0, 1, 1);


        retranslateUi(ModifyOrder);

        modifyOrderTabs->setCurrentIndex(0);


        QMetaObject::connectSlotsByName(ModifyOrder);
    } // setupUi

    void retranslateUi(QDialog *ModifyOrder)
    {
        ModifyOrder->setWindowTitle(QApplication::translate("ModifyOrder", "Dialog", nullptr));
        addButton1_3->setText(QApplication::translate("ModifyOrder", "Rajouter", nullptr));
        delButton1_3->setText(QApplication::translate("ModifyOrder", "Supprimer", nullptr));
        validerButton1_3->setText(QApplication::translate("ModifyOrder", "Valider", nullptr));
        modifyOrderTabs->setTabText(modifyOrderTabs->indexOf(aperoTab), QApplication::translate("ModifyOrder", "Ap\303\251ritifs", nullptr));
        addButton1->setText(QApplication::translate("ModifyOrder", "Rajouter", nullptr));
        delButton1->setText(QApplication::translate("ModifyOrder", "Supprimer", nullptr));
        validerButton1->setText(QApplication::translate("ModifyOrder", "Valider", nullptr));
        modifyOrderTabs->setTabText(modifyOrderTabs->indexOf(entreesTab), QApplication::translate("ModifyOrder", "Entr\303\251es", nullptr));
        addButton2->setText(QApplication::translate("ModifyOrder", "Rajouter", nullptr));
        delButton2->setText(QApplication::translate("ModifyOrder", "Supprimer", nullptr));
        validerButton2->setText(QApplication::translate("ModifyOrder", "Valider", nullptr));
        modifyOrderTabs->setTabText(modifyOrderTabs->indexOf(platsTab), QApplication::translate("ModifyOrder", "Plats", nullptr));
        addButton3->setText(QApplication::translate("ModifyOrder", "Rajouter", nullptr));
        delButton3->setText(QApplication::translate("ModifyOrder", "Supprimer", nullptr));
        validerButton3->setText(QApplication::translate("ModifyOrder", "Valider", nullptr));
        modifyOrderTabs->setTabText(modifyOrderTabs->indexOf(dessertsTab), QApplication::translate("ModifyOrder", "Desserts", nullptr));
        addButton4->setText(QApplication::translate("ModifyOrder", "Rajouter", nullptr));
        delButton4->setText(QApplication::translate("ModifyOrder", "Supprimer", nullptr));
        validerButton4->setText(QApplication::translate("ModifyOrder", "Valider", nullptr));
        modifyOrderTabs->setTabText(modifyOrderTabs->indexOf(boissonsTab), QApplication::translate("ModifyOrder", "Boissons", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ModifyOrder: public Ui_ModifyOrder {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MODIFYORDER_H

/* === This file is part of Calamares - <https://github.com/calamares> ===
 *
 *   SPDX-FileCopyrightText: 2021 Anke Boersma <demm@kaosx.us>
 *   SPDX-License-Identifier: GPL-3.0-or-later
 *   License-Filename: LICENSE
 *
 *   Calamares is Free Software: see the License-Identifier above.
 *
 */

import io.calamares.core 1.0
import io.calamares.ui 1.0

import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Layouts 1.3
import org.kde.kirigami 2.7 as Kirigami
import QtGraphicalEffects 1.0
import QtQuick.Window 2.3

Page {

    id: finished
    // needs to come from umount.conf
    property var configdestLog: "/var/log/Calamares.log"

    width: parent.width
    height: parent.height

    header: Kirigami.Heading {
        width: parent.width
        height: 80
        id: header
        Layout.fillWidth: true
        horizontalAlignment: Qt.AlignHCenter
        color: "white"
        level: 1
        text: qsTr("Installation Completed")

        Text {
            anchors.top: header.bottom
            anchors.horizontalCenter: parent.horizontalCenter
            horizontalAlignment: Text.AlignHCenter
            font.pointSize: 14
            color: "white"
            text: qsTr("%1 has been installed on your computer.<br/>
            You may now restart into your new system, or continue using the Live environment.").arg(Branding.string(Branding.ProductName))
        }

        Image {
            source: "logo.png"
            anchors.top: header.bottom
            anchors.topMargin: 80
            anchors.horizontalCenter: parent.horizontalCenter
            width: 192
            height: 192
            mipmap: true
        }
    }

    RowLayout {
        Layout.alignment: Qt.AlignRight|Qt.AlignVCenter
        anchors.centerIn: parent
        spacing: 6

        Button {
            id: button
            text: qsTr("Close Installer")
            icon.name: "application-exit"
            onClicked: { ViewManager.quit(); }
            //onClicked: console.log("close calamares");
        }

        Button {
            text: qsTr("Restart System")
            icon.name: "system-reboot"
            onClicked: {
                config.doRestart(true);
                ViewManager.quit();
            }
        }
    }

    Item {

        Layout.fillHeight: true
        Layout.fillWidth: true
        anchors.bottom: parent.bottom
        anchors.bottomMargin : 128
        anchors.horizontalCenter: parent.horizontalCenter

        Text {
            anchors.centerIn: parent
            anchors.top: parent.top
            horizontalAlignment: Text.AlignHCenter
            color: "white"
            text: qsTr("<p>A full log of the install is available as installation.log in the home directory of the Live user.<br/>
            This log is copied to %1 of the target system.</p>").arg(configdestLog)
        }
    }

    function onActivate() {
    }

    function onLeave() {
    }
}

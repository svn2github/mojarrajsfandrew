/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package j2meDemo.model;

import javax.faces.component.UISelectOne;

public class SetupForm {
    private String size = "3";
    private String position = "";
    private UISelectOne boardSize;
    private GameBoard gameBoard;

    // PROPERTY: size
    public String getSize() {
/*
      if (battleGround.getAvailableSizes().size() > 0)
         size = ((SelectItem) 
            battleGround.getAvailableSizes().get(0)).getLabel();
*/
        return size;
    }

    public void setSize(String newSize) {
        this.size = newSize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String newPosition) {
        this.position = newPosition;
    }

    public UISelectOne getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(UISelectOne boardSize) {
        this.boardSize = boardSize;
    }

    public void setGameBoard(GameBoard newGameBoard) {
        this.gameBoard = newGameBoard;
    }

    public String play() {
        // client moves
        if (!gameBoard.move()) {
            return "play";
        }
        if (gameBoard.gameComplete() == gameBoard.CLIENT_PLAYER) {
            return "won";
        } else if (gameBoard.gameComplete() == gameBoard.DRAW) {
            return "draw";
        }
        // server moves
        gameBoard.randomMove();
        if (gameBoard.gameComplete() == gameBoard.SERVER_PLAYER) {
            return "lost";
        } else if (gameBoard.gameComplete() == gameBoard.DRAW) {
            return "draw";
        }
        // we're not done yet...
        return "play";
    }

    public String submit() {
        return "play";
    }
}

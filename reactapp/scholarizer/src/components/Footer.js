import React from "react";
import ScholarizerLogo from "./images/ScholarizerLogo.png";
import './Footer.css'

//TODO: import logos
function Footer() {
    return (

        <footer className="footer">
            <div className="footer__container">
                <div className="footer__text">
                    <div className="footer-credits">
                        Made using data from the <a className="footer-link" href="https://www.semanticscholar.org/">Semantic
                        Scholar</a> API.
                    </div>
                </div>
                <div className="footer__logo">
                </div>
                <div className="footer__social">
                    Made at <a className="footer-link" href="https://www.kit.edu/english/index.php">KIT</a>.
                </div>
            </div>
        </footer>
    );

}

export default Footer;
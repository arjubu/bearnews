import Image from "next/image";
import Link from "next/link";
import SocialLink from "../../data/social/SocialLink.json";

const FooterOne = () => {
  return (
    <footer className="page-footer bg-grey-dark-key">
      <div className="container">
        <div className="footer-top">
          <div className="row">
            <div className="col-lg-2 col-md-4 col-6">
              <div className="footer-widget">
                <h2 className="footer-widget-title">Something</h2>
                <ul className="footer-nav">
                  <li>
                  <Link href="/">
                    <a>Thing1</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>Thing2</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>Thing3</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>Thing4</a>
                    </Link>
                  </li>
                  
                </ul>
                {/* End of .footer-nav */}
              </div>
              {/* End of .footer-widget */}
            </div>
            {/* End of .col-lg-2 */}
            <div className="col-lg-2 col-md-4 col-6">
              <div className="footer-widget">
                <h2 className="footer-widget-title">Some</h2>
                <ul className="footer-nav">
                  <li>
                    <Link href="/">
                        <a>Some1</a>
                    </Link>
                  </li>
                  <li>
                    <Link href="/">
                        <a>Some2</a>
                    </Link>
                  </li>
                  <li>
                    <Link href="/">
                        <a>Some3</a>
                    </Link>
                  </li>
                  
                </ul>
                {/* End of .footer-nav */}
              </div>
              {/* End of .footer-widget */}
            </div>
            {/* End of .col-lg-2 */}
            <div className="col-lg-2 col-md-4 col-6">
              <div className="footer-widget">
                <h2 className="footer-widget-title">Bala</h2>
                <ul className="footer-nav">
                  <li>
                  <Link href="/">
                    <a>Bala</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>Bala</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>Bala</a>
                    </Link>
                  </li>
                 
                </ul>
                {/* End of .footer-nav */}
              </div>
              {/* End of .footer-widget */}
            </div>
            {/* End of .col-lg-2 */}
            <div className="col-lg-2 col-md-4 col-6">
              <div className="footer-widget">
                <h2 className="footer-widget-title">LOLOLOL</h2>
                <ul className="footer-nav">
                  <li>
                  <Link href="/">
                    <a>LOLOLOL</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>LOLOLOL</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>LOLOLOL</a>
                    </Link>
                  </li>
                  
                </ul>
                {/* End of .footer-nav */}
              </div>
              {/* End of .footer-widget */}
            </div>
            {/* End of .col-lg-2 */}
            <div className="col-lg-2 col-md-4 col-6">
              <div className="footer-widget">
                <h2 className="footer-widget-title">TaG</h2>
                <ul className="footer-nav">
                  <li>
                  <Link href="/">
                    <a>TaG</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>TaG</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>TaG</a>
                    </Link>
                  </li>
                  
                </ul>
                {/* End of .footer-nav */}
              </div>
              {/* End of .footer-widget */}
            </div>
            {/* End of .col-lg-2 */}
            <div className="col-lg-2 col-md-4 col-6">
              <div className="footer-widget">
                <h2 className="footer-widget-title">About</h2>
                <ul className="footer-nav">
                  <li>
                  <Link href="/">
                    <a>We</a>
                    </Link>
                  </li>
                  <li>
                  <Link href="/">
                    <a>Jo</a>
                    </Link>
                  </li>
                  
                </ul>
                {/* End of .footer-nav */}
              </div>
              {/* End of .footer-widget */}
            </div>
            {/* End of .col-lg-2 */}
          </div>
          {/* End of .row */}
        </div>
        {/* End of .footer-top */}
        <div className="footer-mid">
          <div className="row align-items-center">
            <div className="col-md">
              <div className="footer-logo-container">
                <Link href="/">
                    <a> 
                        <Image 
                         src="/images/Bear_Mark_1_Color_01.jpg"
                         alt="footer logo"
                         className="footer-logo"
                         width={60}
                         height={50}
                        />
                    </a>
                </Link>
              </div>
              {/* End of .brand-logo-container */}
            </div>
            {/* End of .col-md-6 */}
            <div className="col-md-auto">
              <div className="footer-social-share-wrapper">
                <div className="footer-social-share">
                  <div className="axil-social-title">Find us here</div>
                  <ul className="social-share social-share__with-bg">
                  <li>
                    <a href={SocialLink.fb.url}>
                      <i className={SocialLink.fb.icon} />
                    </a>
                  </li>
                  <li>
                    <a href={SocialLink.twitter.url}>
                      <i className={SocialLink.twitter.icon} />
                    </a>
                  </li>
                  <li>
                    <a href={SocialLink.yt.url}>
                      <i className={SocialLink.yt.icon} />
                    </a>
                  </li>
                  <li>
                    <a href={SocialLink.linked.url}>
                      <i className={SocialLink.linked.icon} />
                    </a>
                  </li>
                  <li>
                    <a href={SocialLink.pinterest.url}>
                      <i className={SocialLink.pinterest.icon} />
                    </a>
                  </li>
                  </ul>
                </div>
              </div>
              {/* End of .footer-social-share-wrapper */}
            </div>
            {/* End of .col-md-6 */}
          </div>
          {/* End of .row */}
        </div>
        {/* End of .footer-mid */}
        <div className="footer-bottom">
          {/* <ul className="footer-bottom-links">
            <li>
                <Link href="/">
                    <a>Terms of Use</a>
                </Link>
            </li>
            <li>
            <Link href="/">
              <a>Accessibility &amp; CC</a>
              </Link>
            </li>

            <li>
            <Link href="/">
              <a>Modern Slavery Act Statement</a>
              </Link>
            </li>
            <li>
            <Link href="/">
              <a>Advertise with us</a>
              </Link>
            </li>

            <li>
                <Link href="/">
                    <a>License Footage</a>
                </Link>
            </li>
            <li>
                <Link href="/">
                    <a>Sitemap</a>
                </Link>
            </li>
          </ul> */}
          {/* End of .footer-bottom-links */}
          <p className="axil-copyright-txt">
            © {new Date().getFullYear()}. All rights reserved by BearWeb.
          </p>
        </div>
        {/* End of .footer-bottom */}
      </div>
      {/* End of .container */}
    </footer>
  );
};

export default FooterOne;

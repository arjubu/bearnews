import Like from "./Like"
const SocialShareBottom = () => {
  return (
    <div className="post-shares m-t-xs-60">
      <div className="title">Operation:</div>
      <ul className="social-share social-share__rectangular">
        <li>
        <Like/>
        </li>
        <li>
          <a href="#" className="btn bg-color-linkedin">
            <i className="fab fa-linkedin-in" />
          </a>
        </li>
      </ul>
    </div>
  );
};

export default SocialShareBottom;
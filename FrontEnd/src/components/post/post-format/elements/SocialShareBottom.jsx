import Like from "./Like"
import Love from "./Love"
const SocialShareBottom = () => {
  return (
    <div className="post-shares m-t-xs-60">
      <div className="title">Operation:</div>
      <ul className="social-share social-share__rectangular">
        <li>
        <Like/>
        </li>
        <li>
          <Love/>
        </li>
      </ul>
    </div>
  );
};

export default SocialShareBottom;
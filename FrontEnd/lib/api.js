import fs from 'fs'
import { join } from 'path'
import matter from 'gray-matter'
import { arch } from 'os';

const postsDirectory = join(process.cwd(), 'posts')

export async function getPostSlugs() {
  //console.log(fs.readdirSync(postsDirectory));
  let Mylist = {};
  Mylist = await fetch('http://localhost:8080/fetchArticleTitles'
  )
    .then(response => {
       
      if (response.status == 200) {
        console.log('go'); 
        return response.json();
        
      } else {
        
        throw new Error('Something went wrong ...');

      }
        
      }).then(data=>{

        return data;
        //console.log(Mylist);
      });
      let a = [];
      Mylist.forEach(myfunction)
      function myfunction(item){
        a.push(item);
      }
      //console.log(a);
      //console.log(JSON.parse(Mylist));
  return a;
}

export async function getPostBySlug(slug, fields = []) {
  const realSlug = slug.toString();
  console.log(typeof realSlug);
  let article = await fetch('http://localhost:8080/fetchArticleById?articleId='+realSlug
  )
    .then(response => {
       
      if (response.status == 200) {
        //console.log('go'); 
        return response.json();
        
      } else {
        console.log(response.status);
        throw new Error('Something went wrong ...');

      }
        
      }).then(data=>{

        return data;
        //console.log(Mylist);
      });
      //console.log(article.contains.tagText);

  const items = {}

  // Ensure only the minimal needed data is exposed
  // fields.forEach((field) => {
  //   if (field === 'slug') {
  //     items[field] = realSlug
  //   }
  //   if (field === 'content') {
  //     items[field] = content
  //   }

  //   if (typeof data[field] !== 'undefined') {
  //     items[field] = data[field]
  //   }
  // })
  items['title'] = article.title;
  items['content'] = article.content;
  items['slug'] = article.id.toString();
  items['cate'] = article.contains.tagText;
  items['author_name'] = article.articleType;
  items['postFormat'] = 'standard';
  items['cate_bg'] = 'bg-color-purple-one';
  items['date'] = article.createdAt;
  items['cate_img'] = '/images/category/travel.png';
  items['featureImg'] =  '/images/posts/post_2.jpg';
  items['author_img'] = '/images/author/amachea_jajah.png';
  items['author_social'] =     
  [
  {icon: "fab fa-facebook-f",
      url: "https://facebook.com/"
    },

  {icon: "fab fa-twitter",
    url: "https://twitter.com/"
  },

  {icon: "fab fa-behance",
    url: "https://www.behance.net/"
  },
 
  {icon: "fab fa-linkedin-in",
    url: "https://linkedin.com"}];

  items['author_bio'] = '';
  //console.log(items);

  return items
}

export async function getAllPosts(fields = []) {
  const slugs = await getPostSlugs();

   const posts = await Promise.all(slugs.map( async(slug) => getPostBySlug(slug, fields)))
     //console.log("posts");
     //console.log(posts);

  return posts;
}


// Get Markdown File Content 

export function getFileContentBySlug(fileName, postsPath) {

    const postFilePath = join(postsPath, `${fileName}.md`)
    const fileContents = fs.readFileSync(postFilePath, 'utf8')

    const { data, content } = matter(fileContents)

    return {
      data,
      content
    }
}





 



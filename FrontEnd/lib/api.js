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
        
        //throw new Error('Something went wrong ...');
        console.log("Something went wrong ...");

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

export async function getBaylorPostSlugs() {
  //console.log(fs.readdirSync(postsDirectory));
  let Mylist = {};
    Mylist = await fetch('http://localhost:8080/fetchBaylorNewsArticleTitles'
  )
    .then(response => {
       
      if (response.status == 200) {
        console.log('go'); 
        return response.json();
        
      } else {
        
        console.log("Something went wrong ...");

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
  // console.log("realSlug");
  // console.log(realSlug);

  //console.log(typeof realSlug);
    let article = await fetch('http://localhost:8080/fetchArticleById?articleId='+realSlug
  )
    .then(response => {
       
      if (response.status == 200) {
        console.log("go"); 
        return response.json();
        
      } else {
        console.log(response.status);
       // throw new Error('Something went wrong ...');

      }
        
      }).then(data=>{

        return data;
        //console.log(Mylist);
      });
      //console.log(article.contains.tagText);

  const items = {}
  //console.log("article");

      //console.log(article);
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
  //console.log(article);
  items['title'] = article.data.titleOfArticle;
  items['content'] = article.data.contentOfArticle;
  items['slug'] = article.data.idOfArticle.toString();
  items['cate'] = article.data.textOfTag;
  items['author_name'] = article.data.firstNameofCreator +" "+article.data.lastNameofCreator;
  items['postFormat'] = 'standard';
  if(article.data.articleType == "BAYLORNEWS"){
    items['cate_bg'] = 'bg-color-green-one';
  }else{
    items['cate_bg'] = 'bg-color-purple-one';
  }
  items['date'] = article.data.timeOfCreation;
  items['cate_img'] = '/images/category/travel.png';
    if(article.data.image==null|| article.data.image==undefined ||article.data.image=="undefined"){
      
    items['featureImg'] =  '/images/posts/download.png';
   
    }else{
      items['featureImg'] = article.data.image;
    }

    if(article.data.thumbLink!=null && article.data.thumbLink!=undefined && article.data.thumbLink!="undefined"  && article.data.thumbLink!=''){
       console.log(article.data.thumbLink);
      items['featureImg'] = article.data.thumbLink;
    }
  items['author_img'] = '/images/author/amachea_jajah.png';
  items['articleType'] = article.data.articleType;
  console.log("data TYPE: "+slug+ " "+article.data.articleType);
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

  return items;
}

export async function getAllPosts(fields = []) {
  const slugs = await getPostSlugs();
  const baylorslugs = await getBaylorPostSlugs();
  baylorslugs.forEach((slug)=>slugs.push(slug));
  //fields.push("articleType");
  //console.log("fields");

  //console.log(fields);
  
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






 



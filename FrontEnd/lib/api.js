import fs from 'fs'
import { join } from 'path'
import matter from 'gray-matter'

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
  const realSlug = slug.replace(/\.md$/, '')
  const fullPath = join(postsDirectory, `${realSlug}.md`)
  const fileContents = fs.readFileSync(fullPath, 'utf8')
  const { data, content } = matter(fileContents)

  const items = {}

  // Ensure only the minimal needed data is exposed
  fields.forEach((field) => {
    if (field === 'slug') {
      items[field] = realSlug
    }
    if (field === 'content') {
      items[field] = content
    }

    if (typeof data[field] !== 'undefined') {
      items[field] = data[field]
    }
  })

  return items
}

export async function getAllPosts(fields = []) {
  const slugs = await getPostSlugs();

   const posts = slugs
     .map((slug) => getPostBySlug(slug, fields))

  return slugs;
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





 


